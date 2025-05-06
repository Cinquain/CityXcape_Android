package com.cityxcape.cityxcape.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cityxcape.cityxcape.components.StreetPassBackground


@Composable
fun CreateUsername(vm: AuthViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        StreetPassBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
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

            Image(
                painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.face),
                contentDescription = "face",
                modifier = Modifier.size(100.dp)
            )

            OutlinedTextField(
                value = vm.username,
                onValueChange = { vm.username = it},
                modifier = Modifier.width(300.dp).height(60.dp),
                label = if (vm.username.isEmpty()) {
                    { Text("Create a Username")}
                } else {null},
                textStyle = TextStyle(fontWeight = FontWeight.Light, fontSize = 22.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }
                ),
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.White
                ),
            )

            Text(
                text = "Set Your Gender",
                color = Color(0xFFF59b4d),
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 15.dp)
            )


           Row(
               verticalAlignment = Alignment.CenterVertically
           ) {
               Text(
                   text = if (vm.isMale) { "Male" } else {"Female"},
                   color = if (vm.isMale) {Color.White} else { Color(0xFFF59b4d)},
                   fontSize = 13.sp,
               )
               Switch(
                   checked = vm.isMale,
                   onCheckedChange = {
                       vm.isMale = it
                   },
               )
           }

            Spacer(Modifier.height(30.dp))

            FilledTonalButton(
                onClick = {
                    vm.setUsernameGender()
                },
                colors = ButtonDefaults.buttonColors(containerColor =
                    if (vm.username.count() > 3){ Color(0xFF00C1EA) } else {
                        Color.Gray}),
                modifier = Modifier.width(120.dp).height(40.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Done",
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CreateUsernamePreview() {
    CreateUsername(AuthViewModel())
}