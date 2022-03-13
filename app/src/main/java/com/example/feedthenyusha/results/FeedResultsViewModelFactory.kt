package com.example.feedthenyusha.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feedthenyusha.database.dao.FeedResultDao

class FeedResultsViewModelFactory(
    private val dao: FeedResultDao,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedResultsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedResultsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
