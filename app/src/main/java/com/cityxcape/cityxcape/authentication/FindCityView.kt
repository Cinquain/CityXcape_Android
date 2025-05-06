package com.cityxcape.cityxcape.authentication

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import android.Manifest
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.ui.draw.clip
import com.cityxcape.cityxcape.components.StreetPassBackground
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
fun FindCityView(vm: AuthViewModel) {

    val context = LocalContext.current
    val fusedLocation = remember { LocationServices.getFusedLocationProviderClient(context) }
    val defaultLocation = rememberUpdatedMarkerState(position = LatLng(44.97263913267112, -93.21218971592698))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(44.97263913267112, -93.21218971592698),15f)
    }
    val location = vm.userLocation

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->

            if (isGranted && ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            ) {

                fusedLocation.lastLocation
                    .addOnSuccessListener { location ->
                        location.let {
                            val position = LatLng(it.latitude, it.longitude)
                            vm.userLocation = position
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(position, 15f)
                        }
                }

            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }

    )


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

            Spacer(Modifier.height(100.dp))

            Text(
                text = "Let's Find Your City",
                color = Color.White,
                fontWeight = FontWeight.Thin,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxWidth().height(400.dp),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = false)
                ) {

                    Marker(
                        state = defaultLocation,
                        title = "You are here"
                    )

                }
            }

            FilledTonalButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor =
                    if (vm.userLocation == null){ Color.Gray } else {
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
                    androidx.compose.material.Text(
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
fun FindCityPreview() {
    FindCityView(AuthViewModel())
}