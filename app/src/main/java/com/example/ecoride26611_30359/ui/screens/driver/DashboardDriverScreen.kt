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
import java.net.URLEncoder
import java.net.URLDecoder
import java.util.*

@Composable
fun DashboardDriverScreen(
    navController: NavHostController,
    userId: Int,
    viewModel: DashboardDriverViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val origemBonita = remember(uiState.origemLabel) {
        URLDecoder.decode(uiState.origemLabel, "UTF-8")
    }

    val destinoBonito = remember(uiState.destinoLabel) {
        URLDecoder.decode(uiState.destinoLabel, "UTF-8")
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            viewModel.updateDataHora("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
            }
            Text("Criar Viagem", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(20.dp))

        uiState.errorMessage?.let {
            Text(it, color = Color.Red, fontSize = 14.sp)
        }

        // ORIGEM
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = origemBonita,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text("Origem *") },
                isError = uiState.errorMessage != null && origemBonita.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { navController.navigate("map/origem") }
            )
        }

        Spacer(Modifier.height(16.dp))

        // DESTINO
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = destinoBonito,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                label = { Text("Destino *") },
                isError = uiState.errorMessage != null && destinoBonito.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { navController.navigate("map/destino") }
            )
        }

        Spacer(Modifier.height(16.dp))

        // DATA
        OutlinedTextField(
            value = uiState.dataHora,
            onValueChange = {},
            readOnly = true,
            label = { Text("Data da Viagem *") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.CalendarMonth, null)
                }
            },
            isError = uiState.errorMessage != null && uiState.dataHora.isBlank()
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
                viewModel.validarDados { oriId, destId, data, lug ->
                    navController.navigate(
                        "${AppRoutes.CheckoutDriver.route}?" +
                                "origemId=$oriId&destinoId=$destId&data=$data&lugares=$lug"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Avançar para Checkout", color = Color.White)
        }
    }
}
