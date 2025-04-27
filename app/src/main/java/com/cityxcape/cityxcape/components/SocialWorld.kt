package com.cityxcape.cityxcape.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cityxcape.cityxcape.models.User


@Composable
fun SocialWorld(user: User) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(com.cityxcape.cityxcape.R.drawable.honeycombwave),
                contentScale = ContentScale.Crop
            )
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)
        ) {

            SocialWorldHeader(user)

            Divider(modifier = Modifier.height(0.2.dp), color = Color.White)

            Spacer(modifier = Modifier.height(10.dp))

            user.worlds.forEach { world ->
                WorldBubble(world)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }

}


@Composable
fun SocialWorldHeader(user: User) {
    Row(
        modifier = Modifier.padding(bottom = 5.dp)
    ) {
        Text(
            text = "${user.username}'s World",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Thin
        )
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.hexagons3),
            contentDescription = "Honeycomb",
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )
    }
}




@Preview(showBackground = true)
@Composable
fun SocialWorldPreview() {
    SocialWorld(
        user = User.guest()
    )
}