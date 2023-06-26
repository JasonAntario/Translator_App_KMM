package com.dsankovsky.translatorappkmm.di

import com.dsankovsky.translatorappkmm.database.TranslateDatabase
import com.dsankovsky.translatorappkmm.translate.data.history.SqlDelightHistoryDataSource
import com.dsankovsky.translatorappkmm.translate.data.local.DatabaseDriverFactory
import com.dsankovsky.translatorappkmm.translate.data.remote.HttpClientFactory
import com.dsankovsky.translatorappkmm.translate.data.translate.KtorTranslateClient
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryDataSource
import com.dsankovsky.translatorappkmm.translate.domain.translate.TranslateClient
import com.dsankovsky.translatorappkmm.translate.domain.translate.TranslateUseCase

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }
}