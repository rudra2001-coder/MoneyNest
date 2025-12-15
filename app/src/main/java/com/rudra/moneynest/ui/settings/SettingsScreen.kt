package com.rudra.moneynest.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rudra.moneynest.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val isBiometricLockEnabled by viewModel.isBiometricLockEnabled.collectAsState()
    val currency by viewModel.currency.collectAsState()
    val isCurrencyDialogShown by viewModel.isCurrencyDialogShown.collectAsState()
    val isResetAppDialogShown by viewModel.isResetAppDialogShown.collectAsState()

    if (isCurrencyDialogShown) {
        CurrencySelectionDialog(
            onCurrencySelected = viewModel::onCurrencyChange,
            onDismiss = viewModel::dismissCurrencyDialog
        )
    }

    if (isResetAppDialogShown) {
        ResetAppDialog(
            onConfirm = {
                viewModel.resetApp()
                viewModel.dismissResetAppDialog()
            },
            onDismiss = viewModel::dismissResetAppDialog
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SecuritySettingsCard(
                    isBiometricLockEnabled = isBiometricLockEnabled,
                    onBiometricLockEnabledChange = viewModel::setBiometricLockEnabled,
                    onResetSecurityClick = viewModel::resetSecuritySettings
                )
            }
            item {
                DataSettingsCard(
                    navController = navController,
                    onResetAppClick = viewModel::showResetAppDialog
                )
            }
            item {
                GeneralSettingsCard(
                    currency = currency,
                    onCurrencyClick = viewModel::showCurrencyDialog
                )
            }
        }
    }
}

@Composable
private fun SecuritySettingsCard(
    isBiometricLockEnabled: Boolean,
    onBiometricLockEnabledChange: (Boolean) -> Unit,
    onResetSecurityClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Security", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem(title = "Enable Biometric Lock", onClick = { onBiometricLockEnabledChange(!isBiometricLockEnabled) }) {
                Switch(checked = isBiometricLockEnabled, onCheckedChange = onBiometricLockEnabledChange)
            }
            SettingItem(title = "Reset Security", onClick = onResetSecurityClick)
        }
    }
}

@Composable
private fun DataSettingsCard(
    navController: NavController,
    onResetAppClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Data Management", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem(title = "Backup Data", onClick = { /* TODO */ })
            SettingItem(title = "Restore Data", onClick = { /* TODO */ })
            SettingItem(title = "Generate Report", onClick = { navController.navigate(Screen.Report.route) })
            SettingItem(title = "Reset App", onClick = onResetAppClick)
        }
    }
}

@Composable
private fun GeneralSettingsCard(
    currency: String,
    onCurrencyClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "General", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem(title = "Currency", onClick = onCurrencyClick) {
                Text(text = currency)
            }
        }
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit, content: @Composable (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        if (content != null) {
            content()
        }
    }
}

@Composable
private fun CurrencySelectionDialog(
    onCurrencySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val currencies = listOf("USD", "EUR", "GBP", "INR") // Add more currencies as needed

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Currency") },
        text = {
            LazyColumn {
                items(currencies.size) {
                    Text(
                        text = currencies[it],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCurrencySelected(currencies[it]); onDismiss() }
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun ResetAppDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Reset App") },
        text = { Text("Are you sure you want to reset the app? All of your data will be lost.") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Reset")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
