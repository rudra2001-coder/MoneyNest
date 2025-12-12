package com.rudra.moneynest.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rudra.moneynest.ui.dashboard.components.BudgetProgressSection
import com.rudra.moneynest.ui.dashboard.components.InsightsSection
import com.rudra.moneynest.ui.dashboard.components.MonthlySummaryCards
import com.rudra.moneynest.ui.dashboard.components.QuickActionsRow
import com.rudra.moneynest.ui.dashboard.components.RecentTransactionsList
import com.rudra.moneynest.ui.dashboard.components.SpendingPieChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navController: NavController
) {
    val monthlyBudget by viewModel.monthlyBudget.collectAsState()
    val spendingByCategory by viewModel.spendingByCategory.collectAsState()
    val budgetProgress by viewModel.budgetProgress.collectAsState()
    val recentTransactions by viewModel.recentTransactions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dashboard") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            MonthlySummaryCards(monthlyBudget = monthlyBudget)
            SpendingPieChart(spendingByCategory = spendingByCategory)
            BudgetProgressSection(budgetProgress = budgetProgress)
            RecentTransactionsList(transactions = recentTransactions)
            InsightsSection()
            QuickActionsRow(navController = navController)
            Spacer(modifier = Modifier.height(16.dp)) // Add some space at the bottom
        }
    }
}
