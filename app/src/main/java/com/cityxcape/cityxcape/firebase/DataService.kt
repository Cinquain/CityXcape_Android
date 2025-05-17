package com.cityxcape.cityxcape.firebase

import android.content.Context
import android.nfc.Tag
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.cityxcape.cityxcape.models.Location
import com.cityxcape.cityxcape.models.User
import com.cityxcape.cityxcape.models.World
import com.cityxcape.cityxcape.utilities.PreferencesManager
import com.google.android.gms.auth.api.Auth
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.*
import java.util.Date

object DataService {

    private  val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private var users = mutableListOf<User>()

    var checkinListener: ListenerRegistration? = null


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

    suspend fun getUserCredentials() : User {
        val uid: String = AuthService.uid ?: throw NoSuchElementException("No user id found")
        return getUser(uid)
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
        var dataList = mutableListOf<Map<String, Map<String, Any>>>()
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


    //MARK: LOCATION FUNCTIONS

    suspend fun getLocationFrom(spotId: String) : Location {
        try {
            val document = db.collection("locations").document(spotId).get().await()
            val data = document.data ?: throw NoSuchElementException("No Location Found for this Id")
            return Location.createFromMap(data)
        } catch (e: Exception) {
            throw  e
        }
    }

    fun getCheckedInUsers(spotId: String, completion: (Result<List<User>>) -> Unit) {
        val uid: String = AuthService.uid ?: run {
            completion(Result.failure(NoSuchElementException("No user id found")))
            return
        }
        val reference = db.collection("locations").document(spotId).collection("checkins")

        checkinListener?.remove()
        checkinListener = reference.addSnapshotListener { snapshot, error ->


            if (error != null) {
                completion(Result.failure(error))
                return@addSnapshotListener
            }

            val snapshot = snapshot ?: run {
                completion(Result.failure(IllegalStateException("Empty data snapshot")))
                return@addSnapshotListener
            }

            var changed = false
           for (change in snapshot.documentChanges) {
               val docId = change.document.id
               if (docId == uid) continue

               if (change.type == DocumentChange.Type.ADDED) {
                   val data = change.document.data
                   val user = User.CreateFromMap(data)
                   users.add(user)
                   changed = true
               }

               if (change.type == DocumentChange.Type.REMOVED) {
                    users.removeIf { it.id == docId }
                   changed = true
               }
           }
            if (changed) {
                completion(Result.success(users))
            }


       }
    }

    suspend fun registerUserCheckin(spotId: String, user: User) {
        val reference = db.collection("locations").document(spotId).collection("checkins")
        val userRef = reference.document(user.id)

        Log.d("Firebase", "user world is ${user.worlds}")
        userRef.set(user).await()

        val expiresAt = Timestamp(Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000))
        val ttlData = mapOf(
            "expiresAt" to expiresAt
        )
        userRef.update(ttlData).await()

    }

    suspend fun checkout(spotId: String) {
        val uid : String = AuthService.uid ?: throw NoSuchElementException("No user id found!")
        val reference = db.collection("locations")
            .document(spotId)
            .collection("checkins")
            .document(uid)
        reference.delete().await()
        checkinListener?.remove()
    }


}