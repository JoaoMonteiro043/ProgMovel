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
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes
import com.example.ecoride26611_30359.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CheckoutDriverScreen(
    navController: NavHostController,
    origem: String,
    destino: String,
    data: String,
    lugares: Int
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Resumo da Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))

        Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Itinerário", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("De: $origem", fontWeight = FontWeight.SemiBold)
                Text("Para: $destino", fontWeight = FontWeight.SemiBold)
                Text("Data: $data", color = Color.Gray)
                Text("Lugares: $lugares", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f).height(50.dp)) {
                Text("Editar")
            }

            Button(
                onClick = {
                    val encodedOri = URLEncoder.encode(origem, StandardCharsets.UTF_8.toString())
                    val encodedDest = URLEncoder.encode(destino, StandardCharsets.UTF_8.toString())
                    val encodedData = URLEncoder.encode(data, StandardCharsets.UTF_8.toString())

                    navController.navigate("${AppRoutes.Payment.route}?from=driver&origem=$encodedOri&destino=$encodedDest&data=$encodedData&lugares=$lugares")
                },
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Pagar e Publicar", color = Color.White)
            }
        }
    }
}