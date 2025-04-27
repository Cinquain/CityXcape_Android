package com.cityxcape.cityxcape.streetpass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cityxcape.cityxcape.components.SocialWorld
import com.cityxcape.cityxcape.components.SocialWorldHeader
import com.cityxcape.cityxcape.components.UserBubble
import com.cityxcape.cityxcape.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicStreetPass(user: User) {
    var showSocialWorld by remember { mutableStateOf(false) }

    Box(
         modifier = Modifier.fillMaxSize(),

     ) {
         Background()

         Column(
             modifier = Modifier.fillMaxSize(),
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             SPHeader()

             Spacer(modifier = Modifier.height(50.dp))

             UserBubble(
                 imageUrl = user.imageUrl,
                 size = 300.dp,
                 onClick = {}
             )

             Text(
                 text = user.username,
                 color = Color.White,
                 fontWeight = FontWeight.Light,
                 fontSize = 25.sp,
                 modifier = Modifier.padding(top = 10.dp)
             )
             Spacer(modifier = Modifier.height(50.dp))

             Surface(
                 color = Color.Black,
                 modifier = Modifier
                     .height(44.dp)
                     .width(200.dp)
                     .clickable( onClick = { showSocialWorld = !showSocialWorld }),
                 shape = RoundedCornerShape(percent = 50)
             ) {

                 Row(
                     modifier = Modifier.padding(10.dp),
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.Center
                 ) {
                    Text(
                        text = "Social World",
                        color = Color(0xFFF59b4d),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                     Icon(
                         painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.hexagons3),
                         contentDescription = "View ${user.username} Social World",
                         tint = Color.Unspecified,
                         modifier = Modifier.height(20.dp).aspectRatio(1f)
                     )
                 }

             }

             Spacer(modifier = Modifier.height(50.dp))

             Surface(
                 color = Color(0xFFF59b4d),
                 modifier = Modifier
                     .height(44.dp)
                     .width(220.dp)
                     .clickable(onClick = {}),
                 shape = RoundedCornerShape(percent = 50),
             ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CONNECT",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        letterSpacing = 2.sp
                    )

                    Spacer( modifier = Modifier.width(5.dp))

                    Icon(
                        imageVector = Icons.Default.ChatBubble,
                        modifier = Modifier.height(20.dp),
                        contentDescription = "chat"
                    )
                }
             }

             if (showSocialWorld) {
                 ModalBottomSheet(
                     onDismissRequest = {showSocialWorld = false},
                     dragHandle = null
                     ) {
                     SocialWorld(user)
                 }
             }

             Spacer(modifier = Modifier.fillMaxSize())
         }
     }
}



@Composable
fun SPHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp),
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
fun Background() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.orangepaths),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PublicStreetPassPreview() {
    PublicStreetPass(user = User.guest())
}