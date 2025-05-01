package com.cityxcape.cityxcape.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp


@Composable
fun AppleButton(
    isLoading: MutableState<Boolean>,
    onClick: () -> Unit
) {
        Surface(
            color = Color.Blue.copy(alpha = 0.5f),
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
                    imageVector = Icons.Filled.Mail,
                    contentDescription = "Google Button",
                    tint = Color.White,
                    modifier = Modifier
                        .height(20.dp)
                        .aspectRatio(1f)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "Sign Up with Email",
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

@Preview(showBackground = false)
@Composable
fun AppleButtonPreview() {
    val isVisible = remember { mutableStateOf(false) }
    AppleButton(
        isLoading = isVisible,
        onClick = { }
    )
}