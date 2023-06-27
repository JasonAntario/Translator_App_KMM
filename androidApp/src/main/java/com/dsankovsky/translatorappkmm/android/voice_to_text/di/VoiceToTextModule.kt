package com.dsankovsky.translatorappkmm.android.voice_to_text.di

import android.app.Application
import com.dsankovsky.translatorappkmm.android.voice_to_text.data.AndroidVoiceToTextParser
import com.dsankovsky.translatorappkmm.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
class VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(application: Application): VoiceToTextParser{
        return AndroidVoiceToTextParser(application)
    }
}