package com.rudra.moneynest.ui.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Financial Report") })
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
                SummaryCard("Total Income", "$0.00")
            }
            item {
                SummaryCard("Total Expenses", "$0.00")
            }
            item {
                SummaryCard("Net Savings", "$0.00")
            }
            // TODO: Add more detailed report sections here, such as transactions list, spending by category, etc.
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Text(text = value, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
        }
    }
}
