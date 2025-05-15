package com.cityxcape.cityxcape.firebase

import android.content.Context
import android.nfc.Tag
import android.util.Log
import com.cityxcape.cityxcape.models.User
import com.cityxcape.cityxcape.models.World
import com.cityxcape.cityxcape.utilities.PreferencesManager
import com.google.android.gms.auth.api.Auth
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object DataService {

    private  val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    //MARK: USER FUNCTIONS
    suspend fun createUserOrLoginfromGoogle(userId: String, email: String, context: Context) : Boolean {
        val snapshot = db.collection("users").document(userId).get().await()
        val data = snapshot.data

        return if (data != null) {
            val user = User.CreateFromMap(data)
            PreferencesManager.setPreferencesFrom(user, context)
            false
        } else {
            createUser(userId, email)
            PreferencesManager.saveUserId(context,userId)
            return true
        }
    }

    suspend fun createUser(uid: String, email: String) {
        val data: Map<String, Any> = mapOf(
            "id" to uid,
            "email" to email,
            "streetcred" to 1,
            "timestamp" to Timestamp.now()
        )
        db.collection("users").document(uid).set(data).await()
    }

    suspend fun getUser(uid: String) : User {
        try {
            val document = db.collection("users").document(uid).get().await()
            val data = document.data ?: throw NoSuchElementException("No user found for this uid")
            return User.CreateFromMap(data)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun saveNameGender(data: Map<String, Any>) {
        val uid: String = AuthService?.uid ?: throw NoSuchElementException("User is not authenticated")
        db.collection("users").document(uid).update(data).await()
    }

    fun saveProfileImage(imageUrl: String) {
        val uid: String = AuthService.uid ?: return
        val data: Map<String, Any> = mapOf(
            "imageUrl" to imageUrl
        )
        db.collection("users").document(uid).update(data)
    }

    suspend fun updateFcmToken(token: String) {
        val uid: String = AuthService?.uid ?: return

        val data: Map<String, Any> = mapOf(
            "fcmToken" to token,
            "streetcred" to FieldValue.increment(1)
        )
        db.collection("users").document(uid).update(data).await()
    }

    suspend fun updateUserCity(city: String) {
        val uid: String = AuthService?.uid ?: return
        val data: Map<String, Any> = mapOf(
            "city" to city
        )
        db.collection("users").document(uid).update(data).await()
    }

    suspend fun saveUserWorlds(worlds: List<World>) {
        val uid: String = AuthService?.uid ?: return
        var dataList = mutableListOf<Map<String, Any>>()
        worlds.forEach { world ->
           val innermap = mapOf<String, Any>(
               "worldId" to world.id,
               "name" to world.name,
               "memberName" to world.memberName,
               "imageUrl" to world.imageUrl
           )
            val outtermap = mapOf(
                world.id to innermap
            )
            dataList.add(outtermap)
        }
        val data = mapOf<String, Any>(
            "worlds" to dataList,
            "streetcred" to FieldValue.increment(1)
        )
        db.collection("users").document(uid).update(data).await()
    }

    suspend fun deleteUser(context: Context) {
        val uid: String = AuthService?.uid ?: return
        db.collection("users").document(uid).delete().await()
        AuthService.auth.currentUser?.delete()?.await()
        PreferencesManager.clearSharedPreferences(context)
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