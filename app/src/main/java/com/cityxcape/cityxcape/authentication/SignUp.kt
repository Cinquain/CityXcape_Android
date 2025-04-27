package com.cityxcape.cityxcape.authentication

import android.credentials.GetCredentialRequest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.security.SecureRandom
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import com.cityxcape.cityxcape.components.AppleButton
import com.cityxcape.cityxcape.components.GoogleButton
import com.cityxcape.cityxcape.components.StreetPassBackground
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


@Composable
fun SignUp() {
    val isVisible = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        StreetPassBackground()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.dot),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(5.dp)
            )
            Text(
                text = "Create an Account",
                fontWeight = FontWeight.Light,
                color = Color.White,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            GoogleButton(
                isLoading = isVisible,
                onClick = {
                    isVisible.value = true

                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            AppleButton(
                isLoading = isVisible,
                onClick = {}
            )
        }
    }
}


fun generateNonce(length: Int = 32): String {
    val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._"
    val random = SecureRandom()
    return (1..length)
        .map { charset[random.nextInt(charset.length)] }
        .joinToString("")
}





@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp()
}