package com.rudra.moneynest.ui.security

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rudra.moneynest.ui.navigation.Screen

@Composable
fun PinScreen(
    navController: NavController,
    viewModel: PinViewModel = hiltViewModel()
) {
    val pinState by viewModel.pinState.collectAsState()

    var pin by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = pin,
                onValueChange = { pin = it },
                label = { Text("Enter PIN") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )

            Button(onClick = {
                when (pinState) {
                    is PinState.PinNotSet -> viewModel.setPin(pin)
                    is PinState.PinSet -> viewModel.verifyPin(pin)
                    else -> {}
                }
            }) {
                Text(
                    when (pinState) {
                        is PinState.PinNotSet -> "Set PIN"
                        is PinState.PinSet -> "Enter PIN"
                        else -> "Loading"
                    }
                )
            }
        }
    }

    when (pinState) {
        is PinState.PinVerified -> {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Pin.route) { inclusive = true }
            }
        }
        else -> {}
    }
}
