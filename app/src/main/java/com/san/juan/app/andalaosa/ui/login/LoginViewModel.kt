package com.san.juan.app.andalaosa.ui.login

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.san.juan.app.andalaosa.data.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private var _authStatus = MutableLiveData<FirebaseUser?>()
    val authStatus = _authStatus

    val client = loginRepository.repoClient
    val auth = loginRepository.repoAuth

    fun loginWithGogle(data: Intent?){
        loginRepository.authWithGoogle(data)
        loginRepository.loginStatus.observeForever(Observer {
            _authStatus.value = it
        })
    }
}