package com.cityxcape.cityxcape.authentication

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun SignUpWithEmail(vm: AuthViewModel) {
    val context = LocalContext.current
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
                modifier = Modifier.width(300.dp).height(40.dp),
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
                modifier = Modifier.width(300.dp).height(40.dp),
                textStyle = TextStyle(fontWeight = FontWeight.Light, fontSize = 22.sp),
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                )
            )

            Spacer(Modifier.height(20.dp))

            FilledTonalButton(
                onClick = {
                    vm.createUserByEmailAndPassword(context)
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