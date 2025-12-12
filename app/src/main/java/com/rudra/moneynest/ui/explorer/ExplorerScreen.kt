package com.rudra.moneynest.ui.explorer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rudra.moneynest.ui.navigation.Screen

@Composable
fun ExplorerScreen(navController: NavController) {
    Scaffold {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item { Button(onClick = { navController.navigate(Screen.AddTransaction.route) }) { Text("Add Transaction") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.Categories.route) }) { Text("Categories") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.MonthlyBudget.route) }) { Text("Monthly Budget") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.Goals.route) }) { Text("Goals") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.BillTracker.route) }) { Text("Bill Tracker") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.NetWorth.route) }) { Text("Net Worth") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.DataExport.route) }) { Text("Data Export") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.Report.route) }) { Text("Report") } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Button(onClick = { navController.navigate(Screen.Settings.route) }) { Text("Settings") } }
        }
    }
}
