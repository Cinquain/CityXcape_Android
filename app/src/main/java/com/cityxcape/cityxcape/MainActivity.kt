package com.cityxcape.cityxcape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.cityxcape.cityxcape.components.StampView
import com.cityxcape.cityxcape.components.SelfieBubble
import com.cityxcape.cityxcape.ui.theme.CityXcapeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeView()
        }
    }
}


@Composable
fun TestScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(R.drawable.digital_lounge),
            contentScale = ContentScale.Crop
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        SelfieBubble(
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/cityxcape-70313.appspot.com/o/Users%2FEVTU961bfeCZBEBzrBL0%2FSandra.png?alt=media&token=07058b02-642b-44e4-a0ef-3548b250787e",
            size = 300.dp
        )

        Spacer(modifier = Modifier.height(50.dp))

        StampView(name = "Graffiti Pier")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CityXcapeTheme {
        Greeting("Android")
    }
}