package com.dsankovsky.translatorappkmm.translate.domain.history

import com.dsankovsky.translatorappkmm.core.domain.utils.CommonFlow

interface HistoryDataSource {

    fun getHistory(): CommonFlow<List<HistoryItem>>

    suspend fun insertHistoryItem(item: HistoryItem)
}