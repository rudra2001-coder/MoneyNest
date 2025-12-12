package com.rudra.moneynest.ui.monthlybudget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyBudgetScreen(
    viewModel: MonthlyBudgetViewModel = hiltViewModel()
) {
    val monthlyBudget by viewModel.monthlyBudget.collectAsState()
    var plannedAmount by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Monthly Budget") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            monthlyBudget?.let {
                Text("Planned: ${it.plannedAmount}")
                Text("Spent: ${it.spentAmount}")
                Text("Remaining: ${it.remainingAmount}")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = plannedAmount,
                    onValueChange = { plannedAmount = it },
                    label = { Text("Set Planned Amount") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { 
                    viewModel.updatePlannedAmount(plannedAmount.toDoubleOrNull() ?: 0.0)
                    plannedAmount = ""
                }) {
                    Text("Save")
                }
            }
        }
    }
}
