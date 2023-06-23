package com.dsankovsky.translatorappkmm.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryDataSource
import com.dsankovsky.translatorappkmm.translate.domain.translate.TranslateUseCase
import com.dsankovsky.translatorappkmm.translate.presentation.TranslateEvent
import com.dsankovsky.translatorappkmm.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource
): ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase,
            historyDataSource,
            viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent){
        viewModel.onEvent(event)
    }
}