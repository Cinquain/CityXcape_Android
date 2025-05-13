package com.cityxcape.cityxcape.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.pager.PagerState
import java.util.Locale
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.cityxcape.cityxcape.firebase.AuthService
import com.cityxcape.cityxcape.firebase.DataService
import com.cityxcape.cityxcape.models.World
import com.cityxcape.cityxcape.utilities.PreferencesManager
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import okio.IOException

class AuthViewModel: ViewModel() {

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var username by mutableStateOf("")

    var isMale by mutableStateOf(true)

    var fcmToken by mutableStateOf("")

    var imageUrl by mutableStateOf("")

    var errorMessage by mutableStateOf("")

    var city by mutableStateOf("")

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


    fun setUsernameGender(context: Context) {
        val data: Map<String, Any> = mapOf(
            "username" to username,
            "gender" to isMale
        )

        viewModelScope.launch {
            DataService.saveNameGender(data)
            PreferencesManager.saveUsername(context, username)
        }
    }



    fun registerFCMToken() {
        FirebaseMessaging
            .getInstance()
            .token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fcmToken = task.result
                    viewModelScope.launch {
                        DataService.updateFcmToken(fcmToken)
                    }
                } else {
                    Log.e("FCM","Fetching FCM token failed", task.exception)
                }
        }
    }

    suspend fun signInOrSignUp(context: Context) : String {
       return AuthService.signInWithEmail(email, password, context)
    }

    suspend fun saveUsersWorld() {
        DataService.saveUserWorlds(selectedWorlds)
    }


    fun getCityFromLocation(context: Context, latLng: LatLng)  {
        try {
            var geocoder = Geocoder(context, Locale.getDefault())
            var addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
               city = addresses[0].locality ?: ""
                viewModelScope.launch {
                    DataService.updateUserCity(city)
                }
            }
        } catch (e: IOException) {
            Log.e("Geolocation", "${e.message}")
        }

    }

    fun validateOnboarding() : Boolean {
        if (AuthService.auth.currentUser == null) {
            errorMessage = "Please authenticate with Google or Email"
            return  false
        }

        if (username.count() < 3) {
            errorMessage = "Please create a username longer than 3 characters"
            return false
        }

        if (city.isEmpty()) {
            errorMessage = "Please allow CityXcape location permissions"
            return false
        }

        if (imageUrl.isEmpty()) {
            errorMessage = "Please upload a profile picture"
            return false
        }

        if (selectedWorlds.isEmpty()) {
            errorMessage = "Please select three worlds"
            return false
        }

        return true
    }




}