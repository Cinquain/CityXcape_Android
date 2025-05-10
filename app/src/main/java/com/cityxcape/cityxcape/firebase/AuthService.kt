package com.cityxcape.cityxcape.firebase

import androidx.credentials.CredentialManager
import android.content.Context
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import android.content.ContentValues.TAG
import com.cityxcape.cityxcape.R
import androidx.credentials.Credential
import androidx.credentials.ClearCredentialStateRequest
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import kotlinx.coroutines.tasks.await
import java.security.SecureRandom

object AuthService {

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }


    val uid: String?
        get() = auth.currentUser?.uid

    fun isSigned() : Boolean {
        if (auth.currentUser != null) {
            return  true
        } else {
            return  false
        }
    }

    suspend fun signOut(context: Context) {
        val credentialManager = CredentialManager.create(context)
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        auth.signOut()
    }

    suspend fun getCredentialRequest(context: Context) : GetCredentialResponse {
        val credentialManager = CredentialManager.create(context)

        val options = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
            .setFilterByAuthorizedAccounts(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(options)
            .build()

        return credentialManager.getCredential(
            request = request,
            context = context
        )
    }

    suspend fun handleSignInWithGoogle(credential: Credential) : FirebaseUser? {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val credential = GoogleAuthProvider.getCredential(tokenCredential.id, null)
            return auth.signInWithCredential(credential).await().user
        } else {
            Log.w(TAG, "Credential is not of type Google ID!")
            return null
        }
    }



    fun generateNonce(length: Int = 32): String {
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._"
        val random = SecureRandom()
        return (1..length)
            .map { charset[random.nextInt(charset.length)] }
            .joinToString("")
    }

     suspend fun signInWithEmail(email: String, password: String) : String? {
       val user = auth.signInWithEmailAndPassword(email,password).await().user
         //check if user exist, if so, login, if not, create account
       return user?.uid
    }


}