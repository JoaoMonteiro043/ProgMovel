package com.example.ecoride26611_30359.ui.screens.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecoride26611_30359.navigation.AppRoutes
import androidx.navigation.NavHostController

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = viewModel() // Injeção do ViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "EcoRide",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Criar uma conta",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Insira os seus dados para se registrar",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        // Nome - Ligado ao ViewModel
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Email - Ligado ao ViewModel
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Password - Ligado ao ViewModel
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botão Criar Conta - Chama a lógica do ViewModel
        Button(
            onClick = {
                viewModel.performSignIn {
                    navController.navigate(AppRoutes.Home.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Criar conta", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Já tem conta? Entrar!",
            color = Color(0xFF1E88E5),
            fontSize = 15.sp,
            modifier = Modifier.clickable {
                navController.navigate(route = AppRoutes.Login.route)
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Ao clicar em criar conta, você concorda com os nossos\nTermos de Serviço e com a Política de Privacidade",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}


