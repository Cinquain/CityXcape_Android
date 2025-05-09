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
import android.content.Intent
import android.nfc.Tag
import android.widget.Toast
import androidx.credentials.ClearCredentialStateRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.CancellationException
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

    fun handleSignInWithGoogle(credential: Credential) : Boolean {
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


    fun generateNonce(length: Int = 32): String {
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._"
        val random = SecureRandom()
        return (1..length)
            .map { charset[random.nextInt(charset.length)] }
            .joinToString("")
    }

     suspend fun signInWithEmail(email: String, password: String) : String? {
       val user = auth.signInWithEmailAndPassword(email,password).await().user
       return user?.uid
    }


}