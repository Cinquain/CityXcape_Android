package com.cityxcape.cityxcape.firebase

import android.credentials.GetCredentialRequest
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

object AuthService {
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    val uid: String?
        get() = auth.currentUser?.uid


    fun signOut() {
        auth.signOut()
    }



    fun signInwithGoogle(token: String, onResult: (Boolean, FirebaseUser?) -> Unit ) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   onResult(true, auth.currentUser)
                } else {
                    onResult(false, null)
                }
            }
    }



}