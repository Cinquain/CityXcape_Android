package com.cityxcape.cityxcape.checkin

import android.app.Dialog
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import com.cityxcape.cityxcape.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.cityxcape.cityxcape.firebase.AuthService
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

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
                painter = painterResource(id = R.drawable.hexbackground),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
       if (showScan) {
           QRCodeScanner { code ->
               showScan = false
               vm.checkIn()
           }
       } else if (vm.isCheckedIn) {
           DigitalLounge(navController,vm)
       } else {


           CheckinHeader()
           Spacer(Modifier.height(200.dp))

           Image(
               painter = painterResource(id = R.drawable.qrcode),
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
                   if (AuthService.uid == null) {
                       showAlert = !showAlert
                   } else {
                       if (hasPermission) {
                           showScan = !showScan
                       } else {
                           requestPermision = true
                       }
                   }
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



@Composable()
fun CheckinHeader() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 28.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.height(30.dp)
        )
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