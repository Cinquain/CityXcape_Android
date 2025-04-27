package com.cityxcape.cityxcape.checkin
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cityxcape.cityxcape.components.UserBubble
import com.cityxcape.cityxcape.models.User
import com.cityxcape.cityxcape.streetpass.PublicStreetPass


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalLounge(navController: NavHostController, vm: CheckinViewModel) {
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = true)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.digital_lounge),
                contentScale = ContentScale.Crop
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header()

        Spacer(modifier = Modifier.height(50.dp))

        LazyColumn {
            items(vm.users) { user ->
                UserRow(user, vm)
            }
        }

        Spacer(modifier = Modifier.fillMaxSize())

        if (vm.showSP) {

            ModalBottomSheet(
                onDismissRequest = {vm.hideStreetPass()},
                modifier = Modifier.fillMaxSize(),
                sheetState = sheetState
            ) {
                PublicStreetPass(user = vm.currentUser!!)
            }
        }

        LaunchedEffect(Unit) {
            sheetState.expand()
        }


    }
}


@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = com.cityxcape.cityxcape.R.drawable.dotperson),
            contentDescription = "Location Marker",
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(40.dp)
        )

        Text(
            text = "Parlour Bar",
            fontSize = 20.sp,
            fontWeight = FontWeight.Thin,
            color = Color.White
        )

    }
}


@Composable
fun UserRow(user: User, vm: CheckinViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp, start = 10.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

       Column(
           horizontalAlignment = Alignment.CenterHorizontally
       ) {

           UserBubble(
               imageUrl = user.imageUrl,
               size = 100.dp,
               onClick = {
                   vm.setUser(user)
                   vm.showStreetPass()
               }
           )

           Text(
               text = user.username,
               color = Color.White,
               fontWeight = FontWeight.Thin,
               fontSize = 18.sp,

           )
       }

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    vm.setUser(user)
                    vm.showStreetPass()
                          },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF9800) ,
                    contentColor = Color.Black
                ),
                modifier = Modifier.width(150.dp).height(35.dp)
            ) {
                Text(
                    text = "View StreetPass",
                    color = Color.Black,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                color = Color.LightGray,
                thickness = 0.3.dp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DigitalLoungePreview() {
    DigitalLounge(
        navController = rememberNavController(),
        vm = CheckinViewModel()
    )
}