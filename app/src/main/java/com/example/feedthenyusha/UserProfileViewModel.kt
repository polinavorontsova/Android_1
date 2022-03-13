package com.example.feedthenyusha

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class UserProfileViewModel : ViewModel() {
    private val _user: MutableLiveData<GoogleSignInAccount?> = MutableLiveData()

    val user: LiveData<GoogleSignInAccount?>
        get() = _user

    fun setUser(user: GoogleSignInAccount?) {
        _user.value = user
    }

    fun resetUser(context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context, gso)
        client.signOut().addOnCompleteListener {
            setUser(null)
        }
    }
}

