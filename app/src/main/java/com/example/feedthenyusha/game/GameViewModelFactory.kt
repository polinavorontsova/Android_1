package com.example.feedthenyusha.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feedthenyusha.database.dao.FeedResultDao
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class GameViewModelFactory(
    private val dao: FeedResultDao,
    private val user: LiveData<GoogleSignInAccount?>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(dao, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
