package com.cityxcape.cityxcape.checkin

import android.app.Dialog
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import android.content.Context
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cityxcape.cityxcape.firebase.AuthService
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cityxcape.cityxcape.components.AlertBox
import com.cityxcape.cityxcape.utilities.CheckInScreen
import com.cityxcape.cityxcape.utilities.QRCodeScanner
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun Checkin(navController: NavHostController, vm: CheckinViewModel) {
    val context = LocalContext.current

    var showScan by remember { mutableStateOf(false) }
    var showAlert by remember { mutableStateOf(false) }
    var requestPermision by remember { mutableStateOf(false) }
    val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.hexbackground),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
       if (showScan) {
           QRCodeScanner { code ->
               navController.navigate(CheckInScreen.Lounge.route)
           }
       } else {
           Image(
               painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.qrcode),
               contentDescription = "QR Code",
               contentScale = ContentScale.Fit,
               modifier = Modifier
                   .height(220.dp)
           )

           Text(
               text = "Scan QR code",
               color = Color.White,
               fontWeight = FontWeight.Light,
               fontSize = 20.sp
           )
           Spacer(modifier = Modifier.height(10.dp))


           Button(
               onClick = {
//                  if (AuthService.uid == null) {
//                      showAlert = !showAlert
//                  } else {
//                      if (hasPermission) {
//                          showScan = !showScan
//                      } else {
//                          requestPermision = true
//                      }
//                  }
                   showAlert = true
               },
               colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59b4d)),
               modifier = Modifier
                   .width(200.dp)
                   .height(40.dp)
           ) {
               Icon(
                   imageVector = Icons.Filled.QrCode,
                   contentDescription = "Bell Icon",
                   modifier = Modifier.size(20.dp),
                   tint = Color.Black
               )

               Text("Check-In", color = Color.Black, fontWeight = FontWeight.Light)
           }
       }

        AlertBox(
            title = "Profile Required",
            message = "You need an account to check-in",
            confirmText = "Get One",
            dismissText = "Nah",
            onCloseRequest = {showAlert = false},
            onConfirm = {navController.navigate(CheckInScreen.SignUp.route)},
            onDismiss = {showAlert = false},
            showDialog = showAlert
        )

        if (requestPermision) {
            requestCameraPermission(
                context = context,
                onGranted = {}
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DiscoverPreview() {
    Checkin(
        navController = rememberNavController(),
        vm = CheckinViewModel()
    )
}