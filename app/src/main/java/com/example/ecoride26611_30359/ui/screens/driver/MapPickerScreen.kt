package com.example.ecoride26611_30359.ui.screens.driver

import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.data.local.CheckpointEntity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapPickerScreen(
    navController: NavHostController,
    mode: String,
    onPicked: (Int, String) -> Unit
) {
    val context = LocalContext.current
    val dao = AppDatabase.getDatabase(context).checkpointDao()
    var checkpoints by remember { mutableStateOf<List<CheckpointEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        checkpoints = dao.getAll()
    }

    val defaultLocation = LatLng(41.6932, -8.8329)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 13f)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(if (mode == "origem") "Escolher Origem" else "Escolher Destino") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                }
            }
        )
    }) { padding ->

        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(padding),
            cameraPositionState = cameraPositionState
        ) {
            checkpoints.forEach { cp ->
                Marker(
                    state = MarkerState(LatLng(cp.lat, cp.lng)),
                    title = cp.name,
                    onClick = {
                        onPicked(cp.id, cp.name)
                        navController.popBackStack()
                        true
                    }
                )
            }
        }
    }
}
