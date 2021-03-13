package com.san.juan.app.andalaosa.data

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.san.juan.app.andalaosa.R

/**
 * @author Dario Carrizo on 13/3/2021
 **/

class LoginRepository(private val context: Context) {

    private var _loginStatus = MutableLiveData<FirebaseUser?>()
    val loginStatus = _loginStatus

    val gso  = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val repoClient = GoogleSignIn.getClient(context, gso)
    val repoAuth = Firebase.auth

    fun authWithGoogle(data: Intent?){
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val accout = task.getResult(ApiException::class.java)!!
            firebaseAuthWithCredentials(accout.idToken!!)
        }catch (e: ApiException){

        }
    }

    private fun firebaseAuthWithCredentials(token: String){
        val credential = GoogleAuthProvider.getCredential(token, null)
        repoAuth.signInWithCredential(credential)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    _loginStatus.value = repoAuth.currentUser
                }else {
                    _loginStatus.value = null
                }
            }
    }
}