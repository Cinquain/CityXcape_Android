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
import com.cityxcape.cityxcape.utilities.PreferencesManager
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseAuthException
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

    suspend fun handleSignInWithGoogle(credential: Credential, context: Context) : Boolean {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val credential = GoogleAuthProvider.getCredential(tokenCredential.id, null)
            val user = auth.signInWithCredential(credential).await().user
            val uid = user?.uid ?: throw IllegalStateException("User id is null")
            val email = user?.email ?: ""
            return DataService.createUserOrLoginfromGoogle(uid,email,context)
        } else {
            Log.w(TAG, "Credential is not of type Google ID!")
            throw Exception("Credential is not from Google")
        }
    }



    fun generateNonce(length: Int = 32): String {
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._"
        val random = SecureRandom()
        return (1..length)
            .map { charset[random.nextInt(charset.length)] }
            .joinToString("")
    }

     suspend fun signInWithEmail(email: String, password: String, context: Context) : String {
         return  try {
             val result = auth.fetchSignInMethodsForEmail(email).await()
             val methods = result.signInMethods

             if (methods.isNullOrEmpty()) {
                 val user = auth.createUserWithEmailAndPassword(email, password).await().user
                 val uid: String = user?.uid ?: throw IllegalStateException("User id is null")
                 DataService.createUser(uid,email)
                 PreferencesManager.saveUserId(context,uid)
                 "Account Successfully Created"
             } else {
                 val authUser = auth.signInWithEmailAndPassword(email, password).await().user
                 val uid: String = authUser?.uid ?: throw IllegalStateException("User id null")
                 val user = DataService.getUser(uid)
                 PreferencesManager.setPreferencesFrom(user,context)
                 "Successfully Signed In"
             }

         } catch (e: FirebaseAuthException) {
             Log.e("Auth", "Unexpected error: ${e.message}")
             throw e
         } catch (e: Exception) {
             Log.e("Auth", "Unexpected error: ${e.message}")
             throw e
         }
    }


}