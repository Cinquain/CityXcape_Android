package com.cityxcape.cityxcape.authentication

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cityxcape.cityxcape.components.SelfieBubble
import com.cityxcape.cityxcape.components.StreetPassBackground
import com.cityxcape.cityxcape.firebase.ImageManager


@Composable
fun UploadImageView(vm: AuthViewModel) {

    val context = LocalContext.current

    val imgePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri.let {
            if (uri != null)  {
                ImageManager.UploadProfileImage(context, uri, onResult = { result ->
                        result.onSuccess { url ->
                            vm.imageUrl = url
                        }.onFailure {
                            Toast.makeText(context, "Failed to Upload Image", Toast.LENGTH_SHORT).show()
                        }
                })
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            imgePickerLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }

    }

    Box(Modifier.fillMaxSize()) {

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

            Spacer(Modifier.height(100.dp))

            SelfieBubble(
                imageUrl = vm.imageUrl,
                size = 300.dp,
                onClick = {
                    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            android.Manifest.permission.READ_MEDIA_IMAGES
                    } else {
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                    }
                    permissionLauncher.launch(permission)
                }
            )

            Spacer(Modifier.height(50.dp))

            Text(
                text = "Face Pic Only!",
                color = Color.Red,
                fontWeight = FontWeight.Thin,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(10.dp))

            Surface(
                color = Color.White,
                modifier = Modifier
                    .width(200.dp)
                    .height(44.dp)
                    .clickable(onClick = {
                        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            android.Manifest.permission.READ_MEDIA_IMAGES
                        } else {
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        }
                        permissionLauncher.launch(permission)
                    }),
                shape = RoundedCornerShape(50)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Upload a Selfie",
                        color = Color.Blue,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Spacer(Modifier.height(50.dp))

            FilledTonalButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor =
                    if (vm.imageUrl.isEmpty()){ Color.Gray } else {
                        Color(0xFF00C1EA)}),
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
fun UploadImagePreview() {
    UploadImageView(AuthViewModel())
}