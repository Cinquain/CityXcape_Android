package com.cityxcape.cityxcape.checkin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cityxcape.cityxcape.utilities.CheckInScreen

@Composable
fun Checkin(navController: NavHostController, vm: CheckinViewModel) {

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
            fontWeight = FontWeight.Thin,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navController.navigate(CheckInScreen.Lounge.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59b4d)),
            modifier = Modifier
                .width(200.dp)
                .height(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = "Bell Icon",
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )

            Text("Check-In", color = Color.Black, fontWeight = FontWeight.Light)
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