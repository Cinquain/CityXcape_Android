package com.cityxcape.cityxcape.streetpass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cityxcape.cityxcape.components.SelfieBubble
import com.cityxcape.cityxcape.components.StreetPassBackground
import com.cityxcape.cityxcape.models.User
import com.cityxcape.cityxcape.models.World

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

            UserView()

            Spacer(modifier = Modifier.height(20.dp))

            WorldView()


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 25.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "Passport",
                    tint = Color.White,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable(onClick = {})
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Passport",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Thin
                )
            }



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

@Composable
fun UserView() {
    Spacer(modifier = Modifier.height(60.dp))
    SelfieBubble(
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FNwE1WVJY83RcQw4tttAkZ0Vg53Y2%2FprofileImage?alt=media&token=f1a4fd4d-f255-46a3-9d9a-89e7500b9cd1",
        size = 300.dp,
        onClick = {}
    )

    Spacer(modifier = Modifier.height(15.dp))

    Text(text = "Erica",
        color = Color.White,
        fontSize = 25.sp,
        fontWeight = FontWeight.Light
    )

    Text(
        text = "5 StreetCed",
        color = Color.White,
        fontSize = 15.sp,
        fontWeight = FontWeight.Thin,
        modifier = Modifier.padding(top = 2.dp)
    )
}

@Composable
fun WorldView() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        User.guest().worlds.forEach { world ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(world.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "World Image",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.Blue, blendMode = BlendMode.Color),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    clipToBounds = true,
                )

                Spacer(modifier = Modifier.height(2.dp))


            }

            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun StreetPassPreview() {
    StreetPass()
}