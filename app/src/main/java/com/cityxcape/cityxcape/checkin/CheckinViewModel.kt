package com.cityxcape.cityxcape.checkin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cityxcape.cityxcape.models.User


class CheckinViewModel: ViewModel() {

    private val _users = mutableStateListOf<User>()
    val users: List<User> = _users

    init {
        testMethod()
    }

    fun testMethod() {
        User.sampleList().forEach { user ->
            _users.add(user)
        }
    }
}