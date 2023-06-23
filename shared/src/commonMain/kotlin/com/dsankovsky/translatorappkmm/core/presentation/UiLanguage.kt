package com.dsankovsky.translatorappkmm.core.presentation

import com.dsankovsky.translatorappkmm.core.domain.language.Language

expect class UiLanguage {
    val language: Language
    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}