package com.example.feedthenyusha.results

import androidx.lifecycle.ViewModel
import com.example.feedthenyusha.database.dao.FeedResultDao

class FeedResultsViewModel(private val dao: FeedResultDao) : ViewModel() {

    fun playerResults(player: String) = dao.getResultsByPlayerName(player)
}