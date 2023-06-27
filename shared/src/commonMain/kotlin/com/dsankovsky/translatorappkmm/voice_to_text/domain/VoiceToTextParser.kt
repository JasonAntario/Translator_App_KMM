package com.dsankovsky.translatorappkmm.voice_to_text.domain

import com.dsankovsky.translatorappkmm.core.domain.utils.CommonStateFlow

interface VoiceToTextParser {

    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}