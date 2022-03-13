package com.example.feedthenyusha.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feedthenyusha.database.dao.FeedResultDao

class GameViewModelFactory(
    private val dao: FeedResultDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
