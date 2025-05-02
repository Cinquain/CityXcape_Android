package com.cityxcape.cityxcape.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*

class AuthViewModel: ViewModel() {

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var username by mutableStateOf("")

    var isMale by mutableStateOf(true)


}