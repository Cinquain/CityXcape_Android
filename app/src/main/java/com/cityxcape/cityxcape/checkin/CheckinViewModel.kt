package com.cityxcape.cityxcape.checkin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.cityxcape.cityxcape.models.User


class CheckinViewModel: ViewModel() {

    private val _users = mutableStateListOf<User>()
    val users: List<User> = _users

    var showSP by mutableStateOf(false)
        private set

    var currentUser by mutableStateOf<User?>(null)
        private set

    var isCheckedIn by mutableStateOf(false)
        private set

init {
    testMethod()
}

    fun testMethod() {
        User.sampleList().forEach { user ->
            _users.add(user)
        }
    }

    fun checkIn() {
        isCheckedIn = true
    }

    fun checkOut() {
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