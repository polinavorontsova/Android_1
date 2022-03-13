package com.example.feedthenyusha.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedthenyusha.database.dao.FeedResultDao
import com.example.feedthenyusha.database.model.FeedResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameViewModel(
    private val dao: FeedResultDao,
) : ViewModel() {
    private val _satiety: MutableLiveData<Int> by lazy {
        MutableLiveData(0)
    }

    fun loadResults(playerName: String) {
        viewModelScope.launch {
            dao.getLastResultByPlayerName(playerName).collect {
                _satiety.value = it?.satiety ?: 0
            }
        }
    }

    val satiety: LiveData<Int>
        get() = _satiety

    fun updateSatiety() {
        _satiety.value = _satiety.value?.plus(1)
    }

    fun addResult(result: FeedResult) = viewModelScope.launch(Dispatchers.Default) {
        dao.insertNewFeedResult(result)
    }
}
