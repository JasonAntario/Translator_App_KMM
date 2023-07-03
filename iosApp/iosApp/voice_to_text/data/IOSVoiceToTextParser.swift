//
//  IOSVoiceToTextParser.swift
//  iosApp
//
//  Created by Dmitry Sankovsky on 3.07.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine
import Speech

class IOSVoiceToTextParser: VoiceToTextParser, ObservableObject {
    
    private var _state = IOSMutableStateFlow(
        initialValue: VoiceToTextParserState(result: "", error: nil, powerRatio: 0.0, isSpeaking: false)
    )
    var state: CommonStateFlow<VoiceToTextParserState> { _state }
    
    private var micObserver = MicrophonePowerObserver()
    var micPowerRation: Published<Double>.Publisher {
        micObserver.$micPowerRatio
    }
    private var micPowerCancellable: AnyCancellable?
    
    private var recognizer: SFSpeechRecognizer?
    private var audioEngine: AVAudioEngine?
    private var inputNode: AVAudioInputNode?
    private var audioBufferRequest: SFSpeechAudioBufferRecognitionRequest?
    private var recognitionTask: SFSpeechRecognitionTask?
    private var audioSession: AVAudioSession?
    
    
    func cancel() {
        //not needed for IOS
    }
    
    func reset() {
        self.stopListening()
        _state.value = VoiceToTextParserState(result: "", error: nil, powerRatio: 0.0, isSpeaking: false)
    }
    
    func startListening(languageCode: String) {
        updateState(error: nil)
        
        let chosenLocale = Locale.init(identifier: languageCode)
        let suppportedLocale =
        SFSpeechRecognizer.supportedLocales().contains(chosenLocale) ? chosenLocale : Locale.init(identifier: "en-US")
        self.recognizer = SFSpeechRecognizer(locale: suppportedLocale)
        
        guard recognizer?.isAvailable == true else {
            updateState(error: "Speech recognizer is not available")
            return
        }
        
        audioSession = AVAudioSession.sharedInstance()
        
        self.requestPermissons { [weak self] in
            self?.audioBufferRequest = SFSpeechAudioBufferRecognitionRequest()
            
            guard let audioBufferRequest = self?.audioBufferRequest else {
                return
            }
            
            self?.recognitionTask = self?.recognizer?.recognitionTask(
                with: audioBufferRequest,
                resultHandler: { [weak self] (result, error) in
                    guard let result = result else {
                        self?.updateState(error: error?.localizedDescription)
                        return
                    }
                    
                    if result.isFinal {
                        self?.updateState(result: result.bestTranscription.formattedString)
                    }
                })
            
            self?.audioEngine = AVAudioEngine()
            self?.inputNode = self?.audioEngine?.inputNode
            
            let recordingFormat = self?.inputNode?.outputFormat(forBus: 0)
            self?.inputNode?.installTap(onBus: 0, bufferSize: 1024, format: recordingFormat) { buffer, _ in
                self?.audioBufferRequest?.append(buffer)
            }
            
            self?.audioEngine?.prepare()
            
            do {
                try self?.audioSession?.setCategory(
                    .playAndRecord,
                    mode: .spokenAudio,
                    options: .duckOthers
                )
                try self?.audioSession?.setActive(true, options: .notifyOthersOnDeactivation)
                
                self?.micObserver.startObserving()
                
                try self?.audioEngine?.start()
                
                self?.updateState(isSpeaking: true)
                
                self?.micPowerCancellable = self?.micPowerRation
                    .sink(receiveValue: { [weak self] ratio in
                        self?.updateState(powerRatio: ratio)
                    })
            } catch {
                self?.updateState(error: error.localizedDescription, isSpeaking: false)
            }
            
        }
    }
    
    func stopListening() {
        self.updateState(isSpeaking: false)
        
        micPowerCancellable = nil
        micObserver.release()
        
        audioBufferRequest?.endAudio()
        audioBufferRequest = nil
        
        audioEngine?.stop()
        audioEngine = nil
        
        inputNode?.removeTap(onBus: 0)
        try? audioSession?.setActive(false)
        audioSession = nil
    }
    
    private func requestPermissons(onGranted: @escaping() -> Void){
        audioSession?.requestRecordPermission({ [weak self] wasGranted in
            if !wasGranted {
                self?.updateState(error: "You need to grant permission to record the voice!")
                self?.stopListening()
                return
            }
            
            DispatchQueue.main.async {
                SFSpeechRecognizer.requestAuthorization { [weak self] status in
                    if status != .authorized {
                        self?.updateState(error: "You need to grant permission to transcribe audio!")
                        self?.stopListening()
                        return
                    }
                    onGranted()
                }
            }
        })
    }
    
    private func updateState(result: String? = nil, error: String? = nil, powerRatio: CGFloat? = nil, isSpeaking: Bool? = nil) {
        let currentState = _state.value
        _state.value = VoiceToTextParserState(
            result: (result ?? currentState?.result) ?? "",
            error: error ?? currentState?.error,
            powerRatio: Float(powerRatio ?? CGFloat(currentState?.powerRatio ?? 0.0)),
            isSpeaking: (isSpeaking ?? currentState?.isSpeaking) ?? false
        )
    }
}
