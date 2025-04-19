package com.cityxcape.cityxcape.streetpass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cityxcape.cityxcape.components.SelfieBubble
import com.cityxcape.cityxcape.components.StampView
import com.cityxcape.cityxcape.components.StreetPassBackground

@Composable
fun StreetPass() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StreetPassBackground()

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header()
            Spacer(modifier = Modifier.height(100.dp))
            SelfieBubble(
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FNwE1WVJY83RcQw4tttAkZ0Vg53Y2%2FprofileImage?alt=media&token=f1a4fd4d-f255-46a3-9d9a-89e7500b9cd1",
                size = 300.dp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Erica",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Light
                )

            Spacer(modifier = Modifier.height(50.dp))

            StampView(name = "Parlor Bar")
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .padding(top = 20.dp)
        ,
        horizontalArrangement = Arrangement.Start
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Minneapolis",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 2.sp
            )
            Text(
                text = "StreetPass",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 4.sp
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun StreetPassPreview() {
    StreetPass()
}