package com.cityxcape.cityxcape.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cityxcape.cityxcape.R

@Composable
fun Messages() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.hexbackground),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        MessagesHeader()
        Spacer(Modifier.height(200.dp))

        Image(
            painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.chatlarge),
            contentDescription = "QR Code",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(220.dp)
        )

        Text(
            text = "No Conversations",
            color = Color.White,
            fontWeight = FontWeight.Thin,
            fontSize = 20.sp
        )
        Text(
            text = "You don't have any messages",
            color = Color.White,
            fontWeight = FontWeight.Thin,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 3.dp)
        )
    }
}


@Composable()
fun MessagesHeader() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.chatlarge),
            contentDescription = "Logo",
            modifier = Modifier.height(25.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(Modifier.width(5.dp))
        Text(
            text = "Messages",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Thin
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesPreview() {
    Messages()
}