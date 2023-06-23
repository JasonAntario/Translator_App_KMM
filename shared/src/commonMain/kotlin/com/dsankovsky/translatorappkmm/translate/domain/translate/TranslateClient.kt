package com.dsankovsky.translatorappkmm.translate.domain.translate

import com.dsankovsky.translatorappkmm.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}