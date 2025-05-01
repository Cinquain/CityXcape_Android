package com.cityxcape.cityxcape.firebase

import androidx.credentials.CredentialManager
import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import android.content.ContentValues.TAG
import androidx.credentials.ClearCredentialStateRequest
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CancellationException
import java.security.SecureRandom

class AuthService(
    val context: Context,
) {
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val credentialManager = CredentialManager.create(context)

    val uid: String?
        get() = auth.currentUser?.uid

    fun isSigned() : Boolean {
        if (auth.currentUser != null) {
            return  true
        } else {
            return  false
        }
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        auth.signOut()
    }

    suspend fun startSigninWithGoogle() : Boolean {
        try {
            var result = getCredentialRequest()
            return handleSignIn(result.credential)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            return false
        }
    }

    suspend fun getCredentialRequest() : GetCredentialResponse {
        val options = GetGoogleIdOption.Builder()
            .setServerClientId(com.cityxcape.cityxcape.R.string.web_client_id.toString())
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

    fun handleSignIn(credential: Credential) : Boolean {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            // Create Google ID Token
            val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            try {
                // Sign in to Firebase with using the token
                signInWithGoogle(tokenCredential.idToken) {  success, user ->
                    if (success) {
                        val uid = user?.uid
                        //Check if the UID exist in db,
                    // if so, log in, if not create user account

                    } else {
                        Log.e("error", "error signing in to firebase")
                    }
                }
                return  true
            } catch (e: GoogleIdTokenParsingException) {
                println("Google parsing exception error: ${e.message}")
                return  false
            }
        } else {
            Log.w(TAG, "Credential is not of type Google ID!")
            return  false
        }
    }

    fun generateNonce(length: Int = 32): String {
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._"
        val random = SecureRandom()
        return (1..length)
            .map { charset[random.nextInt(charset.length)] }
            .joinToString("")
    }


    fun signInWithGoogle(token: String, onResult: (Boolean, FirebaseUser?) -> Unit ) {
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