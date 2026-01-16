package com.example.ecoride26611_30359.ui.screens.driver

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.R
import com.example.ecoride26611_30359.data.local.AppDatabase
import com.example.ecoride26611_30359.navigation.AppRoutes
import com.example.ecoride26611_30359.utils.GeoUtils

@Composable
fun CheckoutDriverScreen(
    navController: NavHostController,
    origemId: Int,
    destinoId: Int,
    data: String,
    lugares: Int
) {

    val context = LocalContext.current
    val checkpointDao = AppDatabase.getDatabase(context).checkpointDao()

    var origemLabel by remember { mutableStateOf("") }
    var destinoLabel by remember { mutableStateOf("") }
    var distanciaKm by remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(origemId, destinoId) {
        val origem = checkpointDao.getById(origemId)
        val destino = checkpointDao.getById(destinoId)

        if (origem != null && destino != null) {
            origemLabel = origem.name
            destinoLabel = destino.name

            distanciaKm = GeoUtils.distanceKm(
                origem.lat,
                origem.lng,
                destino.lat,
                destino.lng
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Resumo da Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

// 🔹 CARD DO MAPA
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(20.dp))

// 🔹 CARD DA INFORMAÇÃO
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Itinerário", fontWeight = FontWeight.Bold)
                Text("De: $origemLabel")
                Text("Para: $destinoLabel")
                Text("Data: $data", color = Color.Gray)
                Text("Lugares: $lugares", fontWeight = FontWeight.Bold)

                distanciaKm?.let {
                    Text(
                        text = "Distância: %.1f km".format(it),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {

            OutlinedButton(
                onClick = { navController.popBackStack() },
                Modifier.weight(1f).height(50.dp)
            ) {
                Text("Editar")
            }

            Button(
                onClick = {
                    navController.navigate(
                        "${AppRoutes.Payment.route}?" +
                                "from=driver&tripId=0" +
                                "&origemId=$origemId&destinoId=$destinoId" +
                                "&origemLabel=$origemLabel&destinoLabel=$destinoLabel" +
                                "&data=$data&lugares=$lugares"
                    )
                },
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Pagar e Publicar", color = Color.White)
            }
        }
    }
}
