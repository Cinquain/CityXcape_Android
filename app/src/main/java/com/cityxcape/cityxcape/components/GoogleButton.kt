package com.cityxcape.cityxcape.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GoogleButton(
    isLoading: MutableState<Boolean>,
    onClick: () -> Unit) {

    Surface(
        color = Color.Black,
        modifier = Modifier
            .height(44.dp)
            .width(220.dp)
            .clickable { onClick()},
        shape = RoundedCornerShape(percent = 50),
        ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.googlelogo),
                contentDescription = "Google Button",
                tint = Color.Unspecified,
                modifier = Modifier
                    .height(20.dp)
                    .aspectRatio(1f)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Sign Up with Google",
                color = Color.White,
                fontWeight = FontWeight.Light
            )

            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = Color(0xFFF59b4d)
                )
            }
        }
    }
}

@Preview()
@Composable
fun GoogleButtonPreview() {
    val isVisible = remember { mutableStateOf(false) }
    GoogleButton(
        isLoading = isVisible,
        onClick = {
            isVisible.value = true
        }
    )
}