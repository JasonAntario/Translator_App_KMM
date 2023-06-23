package com.dsankovsky.translatorappkmm.translate.data.history

import com.dsankovsky.translatorappkmm.core.domain.utils.CommonFlow
import com.dsankovsky.translatorappkmm.core.domain.utils.toCommonFlow
import com.dsankovsky.translatorappkmm.database.TranslateDatabase
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryDataSource
import com.dsankovsky.translatorappkmm.translate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { historyList ->
                historyList.map {
                    it.toHistoryItem()
                }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            fromText = item.fromText,
            toLanguageCode = item.toLanguageCode,
            toText = item.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }
}