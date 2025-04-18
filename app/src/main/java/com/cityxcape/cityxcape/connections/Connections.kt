package com.cityxcape.cityxcape.connections

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Connections() {
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
            painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.honeycomb),
            contentDescription = "Honeycomb",
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(200.dp)
        )

        Text(
            text = "No Pending Connections",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionsPreview() {
    Connections()
}