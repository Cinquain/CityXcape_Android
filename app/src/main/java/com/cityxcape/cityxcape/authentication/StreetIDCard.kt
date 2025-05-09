package com.cityxcape.cityxcape.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cityxcape.cityxcape.components.SelfieBubble
import com.cityxcape.cityxcape.components.StreetPassBackground


@Composable
fun StreetIDCard(vm: AuthViewModel) {
    Box(Modifier.fillMaxSize()) {

        StreetPassBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(Modifier.height(100.dp))

            Text(
                text = "Welcome to CityXcape",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                letterSpacing = 2.sp,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp, start = 5.dp).fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            SelfieBubble(
                imageUrl = vm.imageUrl,
                size = 300.dp,
                onClick = {}
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = vm.username,
                color = Color.White,
                fontWeight = FontWeight.Thin,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(5.dp))

            Text(
                text = "3 StreetCred",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                fontSize = 15.sp
            )

            Spacer(Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                vm.selectedWorlds.forEach { world ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(world.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "${world.name}",
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(Color.Blue, blendMode = BlendMode.Color),
                            modifier = Modifier.size(60.dp).clip(CircleShape),
                            clipToBounds = true)

                    }
                    Spacer(Modifier.width(10.dp))
                }
            }

            Spacer(Modifier.height(50.dp))

           Row(
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically
           ) {
               vm.selectedWorlds.forEach { world ->
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

            Spacer(Modifier.height(25.dp))

            Surface(
                color = Color(0xFF007AFF),
                modifier = Modifier
                    .width(200.dp)
                    .height(44.dp)
                    .clickable(onClick = {
                        vm.saveUsersWorld()
                    }),
                shape = RoundedCornerShape(50)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Create Account",
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )
                }
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun StreetIDCardPreview() {
    StreetIDCard(AuthViewModel())
}