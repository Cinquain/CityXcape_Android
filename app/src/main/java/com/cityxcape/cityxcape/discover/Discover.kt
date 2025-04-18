package com.cityxcape.cityxcape.discover

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Discover() {
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
    }
}

@Preview(showBackground = true)
@Composable
fun DiscoverPreview() {
    Discover()
}