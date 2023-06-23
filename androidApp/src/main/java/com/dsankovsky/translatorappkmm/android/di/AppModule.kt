package com.dsankovsky.translatorappkmm.android.di

import android.app.Application
import com.dsankovsky.translatorappkmm.database.TranslateDatabase
import com.dsankovsky.translatorappkmm.translate.data.history.SqlDelightHistoryDataSource
import com.dsankovsky.translatorappkmm.translate.data.local.DatabaseDriverFactory
import com.dsankovsky.translatorappkmm.translate.data.remote.HttpClientFactory
import com.dsankovsky.translatorappkmm.translate.data.translate.KtorTranslateClient
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryDataSource
import com.dsankovsky.translatorappkmm.translate.domain.translate.TranslateClient
import com.dsankovsky.translatorappkmm.translate.domain.translate.TranslateUseCase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        historyDataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(client, historyDataSource)
    }
}