package com.cityxcape.cityxcape.authentication

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cityxcape.cityxcape.components.StreetPassBackground


@Composable
fun ChooseWorldView(vm: AuthViewModel) {
    val context = LocalContext.current
    Box(Modifier.fillMaxSize()) {


        StreetPassBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "STREETPASS",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                letterSpacing = 2.sp,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 10.dp, start = 5.dp).fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "What Worlds are You Part Of?",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
               items(vm.worlds) { world ->

                   var checked by remember { mutableStateOf(false) }

                   Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {


                       Row(
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           AsyncImage(
                               model = ImageRequest.Builder(LocalContext.current)
                                   .data(world.imageUrl)
                                   .crossfade(true)
                                   .build(),
                               contentDescription = "World Item",
                               contentScale = ContentScale.Crop,
                               modifier = Modifier
                                   .size(80.dp)
                                   .clickable(enabled = true, onClick = {
                                       if (vm.selectedWorlds.contains(world)) {
                                           vm.selectedWorlds.remove(world)
                                           Toast.makeText(context, "Removed ${world.name} from your world",
                                               Toast.LENGTH_SHORT).show()
                                           checked = false
                                       } else {
                                           vm.selectedWorlds.add(world)
                                           Toast.makeText(context, "Added ${world.name} to your world",
                                               Toast.LENGTH_SHORT).show()
                                           checked = true
                                       }
                                   })
                               ,
                           )
                           Spacer(Modifier.width(20.dp))
                           Text(
                               text = world.name,
                               color = Color.White,
                               fontSize = 18.sp,
                               fontWeight = FontWeight.Light
                           )
                       }


                        Checkbox(
                            checked = checked,
                            onCheckedChange = {
                                if (it) {
                                    vm.selectedWorlds.add(world)
                                    Toast.makeText(context, "Added ${world.name} to your world",
                                        Toast.LENGTH_SHORT).show()
                                    checked = true
                                } else {
                                    vm.selectedWorlds.remove(world)
                                    Toast.makeText(context, "Removed ${world.name} from your world",
                                        Toast.LENGTH_SHORT).show()
                                    checked = false
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.White,
                                uncheckedColor = Color.White,
                                checkmarkColor = Color.White
                            )
                        )
                    }

                   Spacer(modifier = Modifier.height(10.dp))

               }
            }

            Spacer(Modifier.height(50.dp))

            FilledTonalButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor =
                    if (vm.selectedWorlds.isEmpty()){ Color.Gray } else {
                        Color(0xFF00C1EA)}),
                modifier = Modifier.width(120.dp).height(40.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "Done",
                        color = Color.White
                    )
                }
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun ChooseWorldPreview() {
    ChooseWorldView(AuthViewModel())
}