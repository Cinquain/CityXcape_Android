package com.cityxcape.cityxcape.firebase

import android.nfc.Tag
import android.util.Log
import com.cityxcape.cityxcape.models.World
import com.google.android.gms.auth.api.Auth
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object DataService {

    private  val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    //MARK: USER FUNCTIONS
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

    suspend fun createUserFromEmail(uid: String, email: String) {

        val data: Map<String, Any> = mapOf(
            "id" to uid,
            "email" to email,
            "streetcred" to 1,
            "timestamp" to Timestamp
        )

        db.collection("users").document(uid).set(data).await()
    }

    suspend fun saveNameGender(data: Map<String, Any>) {
        val uid: String = AuthService?.uid ?: return
        db.collection("users").document(uid).update(data).await()
    }

    fun saveProfileImage(imageUrl: String) {
        val uid: String = AuthService.uid ?: return

        val data: Map<String, Any> = mapOf(
            "imageUrl" to imageUrl
        )
        db.collection("users").document(uid)
            .update(data)
    }

    fun updateFcmToken(token: String) {
        val uid: String = AuthService?.uid ?: return

        val data: Map<String, Any> = mapOf(
            "fcmToken" to token
        )
        db.collection("users").document(uid)
            .update(data)
    }

    fun deleteUser() {
        val uid: String = AuthService?.uid ?: return

        db.collection("users").document(uid).delete()
        AuthService.auth.currentUser?.delete()


    }


    //MARK: WORLD FUNCTIONS

    suspend fun fetchAllWorlds() : MutableList<World> {
        var worlds: MutableList<World> = mutableListOf()
        val documents = db.collection("worlds").get().await()
        for (document in documents) {
            val data = document.data
            val world = World.CreateFromMap(data)
            worlds.add(world)
        }
        return worlds
    }


}