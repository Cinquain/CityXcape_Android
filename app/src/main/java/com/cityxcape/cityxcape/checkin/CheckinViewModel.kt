package com.cityxcape.cityxcape.checkin

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cityxcape.cityxcape.firebase.DataService
import com.cityxcape.cityxcape.models.Location
import com.cityxcape.cityxcape.models.User
import com.cityxcape.cityxcape.utilities.PreferencesManager
import kotlinx.coroutines.launch


class CheckinViewModel: ViewModel() {

    var users by mutableStateOf<List<User>>(emptyList())

    var showSP by mutableStateOf(false)
        private set

    var showHunt by mutableStateOf(false)
        private set

    var currentUser by mutableStateOf<User?>(null)
        private set

    var isCheckedIn by mutableStateOf(false)
        private set


    var scavengerHunt by mutableStateOf<Location?>(null)
        private set

    var socialHub by mutableStateOf<Location?>(null)
        private set


    var errorMessage by mutableStateOf<String?>(null)




    suspend fun handleScan(code: String) {
        val location = checkin(code)
        if (location.isSocialHub) {
            socialHub = location
            isCheckedIn = true
        } else {
            scavengerHunt = location
            showHunt = true
        }
    }

    suspend fun checkin(spotId: String) : Location{
        val location = DataService.getLocationFrom(spotId)
        socialHub = location
        startListeningToUsers(spotId)
        val user = DataService.getUserCredentials()
        currentUser = user
        DataService.registerUserCheckin(spotId, user)
        //Start a session manager

        return location
    }

    fun startListeningToUsers(spotId: String) {
        DataService.getCheckedInUsers(spotId) { result ->
            result
                .onSuccess { userList ->
                    users = userList.toList()
                    Log.d("Listener", "New user is found")
                }
                .onFailure { error ->
                    errorMessage = error.message
                    users = emptyList()
                }
        }
    }


    fun checkOut(spotId: String) {
        viewModelScope.launch {
            DataService.checkout(spotId)
        }
        isCheckedIn = false
    }

    fun showStreetPass() {
        showSP = true
    }

    fun hideStreetPass() {
        showSP = false
    }

    fun setUser(user: User) {
        currentUser = user
    }

    fun clearUser() {
        currentUser = null
    }
}