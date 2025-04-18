package com.cityxcape.cityxcape.components

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch

@Composable
fun StampView(name: String) {
    val context = LocalContext.current
    val scale  = remember { Animatable(5f) }
    val rotation = remember { Animatable(0f) }
    val textRotation = remember { Animatable(0f) }


    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }

        launch {
            rotation.animateTo(
                targetValue = -35f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }

        launch {
            textRotation.animateTo(
                targetValue = -65f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
        }

        val mediaPlayer = MediaPlayer.create(context, com.cityxcape.cityxcape.R.raw.stamp)
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.stamp),
            contentDescription = "stamp",
            modifier = Modifier
                .size(305.dp)
                .scale(scale.value)
                .rotate(rotation.value)
        )

        Text(
            text = name,
            color = Color.Red,
            fontSize = 30.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .scale(scale.value)
                .rotate(textRotation.value)
        )
    }
}





