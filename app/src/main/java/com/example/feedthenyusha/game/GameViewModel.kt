package com.example.feedthenyusha.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedthenyusha.database.dao.FeedResultDao
import com.example.feedthenyusha.database.model.FeedResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GameViewModel(
    private val dao: FeedResultDao,
    private val user: LiveData<GoogleSignInAccount?>
) : ViewModel() {
    private val _satiety: MutableLiveData<Int> by lazy {
        MutableLiveData(0).also {
            fetchResults()
        }
    }

    private fun fetchResults() {
        user.value?.displayName?.let {
            viewModelScope.launch {
                dao.getLastResultByPlayerName(it).collect {
                    _satiety.value = it.satiety
                }
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
