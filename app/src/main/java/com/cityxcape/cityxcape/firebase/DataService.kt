package com.cityxcape.cityxcape.firebase

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object DataService {

    private  val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun createUser(authResult: AuthResult) {
        val uid: String = authResult.user?.uid ?: return
        val email: String = authResult.user?.email ?: return

        val data: Map<String, Any> = mapOf(
            "id" to uid,
            "email" to email,
            "streetcred" to 1,
            "timestamp" to Timestamp
        )

        try {
            db.collection("users").document(uid).set(data).await()
            Log.d("Firestore", "User successfully created!")
        } catch (e: Exception) {
            Log.e("Firestore", "Error creating user", e)
        }
    }


}