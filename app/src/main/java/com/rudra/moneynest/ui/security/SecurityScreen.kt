package com.rudra.moneynest.ui.security

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rudra.moneynest.ui.navigation.Screen

@SuppressLint("ContextCastToActivity")
@Composable
fun SecurityScreen(
    viewModel: SecurityViewModel = hiltViewModel(),
    navController: NavController
) {
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val context = LocalContext.current as FragmentActivity

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Security.route) { inclusive = true }
            }
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { viewModel.authenticate(context as AppCompatActivity) }) {
                Text("Unlock with Biometrics")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.Pin.route) }) {
                Text("Use PIN")
            }
        }
    }
}
