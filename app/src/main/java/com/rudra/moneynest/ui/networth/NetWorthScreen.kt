package com.rudra.moneynest.ui.networth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.rudra.moneynest.data.local.db.entity.NetWorthItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetWorthScreen(
    viewModel: NetWorthViewModel = hiltViewModel()
) {
    val netWorthItems by viewModel.netWorthItems.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val totalAssets = netWorthItems.filter { it.type == "Asset" }.sumOf { it.amount }
    val totalLiabilities = netWorthItems.filter { it.type == "Liability" }.sumOf { it.amount }
    val netWorth = totalAssets - totalLiabilities

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Net Worth") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Net Worth Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Total Net Worth: $netWorth")
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(netWorthItems) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Text(text = "${item.name}: ${item.amount} (${item.type})", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AddNetWorthItemDialog(
                onDismiss = { showDialog = false },
                onAddItem = {
                    viewModel.addNetWorthItem(it)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
private fun AddNetWorthItemDialog(
    onDismiss: () -> Unit,
    onAddItem: (NetWorthItem) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("Asset") }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Add Net Worth Item", modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row {
                    RadioButton(
                        selected = type == "Asset",
                        onClick = { type = "Asset" }
                    )
                    Text("Asset", modifier = Modifier.padding(start = 8.dp))
                    RadioButton(
                        selected = type == "Liability",
                        onClick = { type = "Liability" }
                    )
                    Text("Liability", modifier = Modifier.padding(start = 8.dp))
                }
                Button(
                    onClick = {
                        val item = NetWorthItem(
                            name = name,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            type = type
                        )
                        onAddItem(item)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add")
                }
            }
        }
    }
}
