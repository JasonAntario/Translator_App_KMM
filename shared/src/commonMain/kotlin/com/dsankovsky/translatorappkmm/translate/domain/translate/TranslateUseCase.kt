package com.dsankovsky.translatorappkmm.translate.domain.translate

import com.dsankovsky.translatorappkmm.core.domain.language.Language
import com.dsankovsky.translatorappkmm.core.domain.utils.Resource
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryDataSource
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryItem

class TranslateUseCase(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromLanguage = fromLanguage,
                fromText = fromText,
                toLanguage = toLanguage
            )
            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText
                )
            )
            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}