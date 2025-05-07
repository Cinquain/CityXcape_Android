package com.cityxcape.cityxcape.authentication

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.cityxcape.cityxcape.firebase.AuthService
import com.cityxcape.cityxcape.firebase.DataService
import com.cityxcape.cityxcape.models.World
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var username by mutableStateOf("")

    var isMale by mutableStateOf(true)

    var fcmToken by mutableStateOf("")

    var imageUrl by mutableStateOf("")

    var signUpWithEmail by  mutableStateOf(false)

    var selectedWorlds = mutableStateListOf<World>()

    private var _worlds = mutableStateListOf<World>()
    val worlds: List<World> = _worlds

    //GPS STATE PROPERTIES
    var userLocation by mutableStateOf<LatLng?>(null)
    init {
       viewModelScope.launch {
           fetchAllWorlds()
       }
    }

    suspend fun fetchAllWorlds() {
        val fetchedWorlds = DataService.fetchAllWorlds()
        _worlds.addAll(fetchedWorlds)
    }


    fun setUsernameGender() {
        val data: Map<String, Any> = mapOf(
            "username" to username,
            "gender" to isMale
        )
        DataService.saveNameGender(data)
    }

    fun registerFCMToken() {
        FirebaseMessaging
            .getInstance()
            .token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fcmToken = task.result
                    DataService.updateFcmToken(fcmToken)
                } else {
                    Log.e("FCM","Fetching FCM token failed", task.exception)
                }
        }
    }

    fun createUserByEmailAndPassword(context: Context) {
        AuthService.signInWithEmail(email, password) { uid ->

            if (uid != null) {
                DataService.createUserFromEmail(uid, email)
                Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT)
                signUpWithEmail = false
            } else {
                Toast.makeText(context, "Error creating user", Toast.LENGTH_SHORT)
            }
        }
    }




}