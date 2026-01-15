package com.example.ecoride26611_30359.ui.screens.driver

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecoride26611_30359.navigation.AppRoutes
import java.util.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun DashboardDriverScreen(
    navController: NavHostController,
    userId: Int,
    viewModel: DashboardDriverViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.updateDataHora("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
            }
            Text("Criar Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        uiState.errorMessage?.let {
            Text(it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
        }

        OutlinedTextField(
            value = uiState.origem,
            onValueChange = { viewModel.updateOrigem(it) },
            label = { Text("Origem *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.origem.isBlank()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.destino,
            onValueChange = { viewModel.updateDestino(it) },
            label = { Text("Destino *") },
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null && uiState.destino.isBlank()
        )

        Spacer(Modifier.height(16.dp))

        // Campo de Data que abre o Calendário
        OutlinedTextField(
            value = uiState.dataHora,
            onValueChange = { },
            label = { Text("Data da Viagem *") },
            modifier = Modifier.fillMaxWidth().clickable { datePickerDialog.show() },
            readOnly = true,
            enabled = false,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.CalendarMonth, "Calendário")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = if(uiState.errorMessage != null && uiState.dataHora.isBlank()) Color.Red else MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.numLugares,
            onValueChange = { viewModel.updateNumLugares(it) },
            label = { Text("Número de Lugares") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(30.dp))

        Button(
            onClick = {
                viewModel.validarDados { ori, dest, data, lug ->
                    val encodedOri = URLEncoder.encode(ori, StandardCharsets.UTF_8.toString())
                    val encodedDest = URLEncoder.encode(dest, StandardCharsets.UTF_8.toString())
                    val encodedData = URLEncoder.encode(data, StandardCharsets.UTF_8.toString())

                    // Navega para o Checkout passando os dados na URL
                    navController.navigate("${AppRoutes.CheckoutDriver.route}?origem=$encodedOri&destino=$encodedDest&data=$encodedData&lugares=$lug")
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Avançar para Checkout", color = Color.White)
        }
    }
}