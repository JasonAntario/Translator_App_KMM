//
//  PowerObserver.swift
//  iosApp
//
//  Created by Dmitry Sankovsky on 3.07.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine
import Speech

class MicrophonePowerObserver: ObservableObject {
    
    private var cancellable: AnyCancellable? = nil
    private var audioRecorder: AVAudioRecorder? = nil
    
    @Published
    private (set) var micPowerRatio = 0.0
    
    private let powerRatioEmissionPerSecond = 20.0 //delay = 50 ms
    
    func startObserving() {
        do {
            let recordingSettings: [String: Any] = [
                AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1
            ]
            
            let recorder = try AVAudioRecorder(
                url: URL(fileURLWithPath: "/dev/null", isDirectory: true),
                settings: recordingSettings
            )
            recorder.isMeteringEnabled = true
            recorder.record()
            self.audioRecorder = recorder
            self.cancellable = Timer.publish(
                every: 1.0 / powerRatioEmissionPerSecond,
                tolerance: 1.0 / powerRatioEmissionPerSecond,
                on: .main,
                in: .common
            )
            .autoconnect()
            .sink(receiveValue: { [weak self] _ in
                recorder.updateMeters()
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0.0
                } else {
                    let normalizeOffset = CGFloat(50 + powerOffset)
                    self?.micPowerRatio = normalizeOffset
                }
            })
        } catch {
            print("Error when observing pmicrophone power: \(error.localizedDescription)")
        }
    }
    
    
    func release() {
        cancellable = nil
        
        audioRecorder?.stop()
        audioRecorder = nil
        
        micPowerRatio = 0.0
    }
}

