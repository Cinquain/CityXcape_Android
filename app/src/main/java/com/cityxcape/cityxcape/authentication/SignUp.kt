package com.cityxcape.cityxcape.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cityxcape.cityxcape.components.AppleButton
import com.cityxcape.cityxcape.components.GoogleButton
import com.cityxcape.cityxcape.components.StreetPassBackground
import com.cityxcape.cityxcape.firebase.AuthService
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(vm: AuthViewModel) {
    val isVisible = remember { mutableStateOf(false) }
    var context = LocalContext.current
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        StreetPassBackground()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "STREETPASS",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                letterSpacing = 2.sp,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 10.dp, start = 5.dp).fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(200.dp))

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
                    scope.launch {
                        AuthService.getCredentialRequest(context)
                        isVisible.value = true
                    }

                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            AppleButton(
                isLoading = isVisible,
                onClick = { vm.signUpWithEmail = true }
            )

            if (vm.signUpWithEmail) {
                ModalBottomSheet(
                    onDismissRequest = { vm.signUpWithEmail = false },
                    dragHandle = null
                ) {
                    SignUpWithEmail(vm)
                }
            }

        }
    }
}








@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp(AuthViewModel())
}