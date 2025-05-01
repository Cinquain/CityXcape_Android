package com.cityxcape.cityxcape.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cityxcape.cityxcape.models.World


@Composable
fun WorldBubble(world: World) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(world.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "World Image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(70.dp)
        )

        Text(
            text = world.memberName,
            fontWeight = FontWeight.Light,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun WorldBubblePreview() {
    WorldBubble(World.sampleWorld())
}