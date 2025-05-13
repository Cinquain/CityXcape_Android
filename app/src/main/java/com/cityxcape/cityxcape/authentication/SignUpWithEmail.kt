package com.cityxcape.cityxcape.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.cityxcape.cityxcape.components.StreetPassBackground
import kotlinx.coroutines.launch
import android.widget.Toast
import com.cityxcape.cityxcape.firebase.DataService
import kotlinx.coroutines.delay

@Composable
fun SignUpWithEmail(vm: AuthViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        StreetPassBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Icon(
                imageVector = Icons.Default.Mail,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )

            Text(
                text = "Sign Up/In with Email",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = vm.email,
                onValueChange = { vm.email = it},
                modifier = Modifier.width(300.dp).height(60.dp),
                textStyle = TextStyle(fontWeight = FontWeight.Light, fontSize = 22.sp),
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = vm.password,
                onValueChange = { vm.password = it},
                modifier = Modifier.width(300.dp).height(60.dp),
                textStyle = TextStyle(fontWeight = FontWeight.Light, fontSize = 22.sp),
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                )
            )

            Spacer(Modifier.height(20.dp))

            FilledTonalButton(
                onClick = {
                    scope.launch {
                        try {
                            val result = vm.signInOrSignUp(context)
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                            delay(2500)
                            vm.signUpWithEmail = false
                        } catch (e: Exception) {
                            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00C1EA)
                ),
                modifier = Modifier.width(150.dp).height(44.dp)
            ) {
                Text(
                    text = "Get Credentials",
                    color = Color.White
                )
            }



        }






    }
}


@Preview(showBackground = true)
@Composable
fun SignUpWithEmailPreview() {
    SignUpWithEmail(AuthViewModel())
}