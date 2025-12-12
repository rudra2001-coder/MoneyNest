package com.rudra.moneynest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudra.moneynest.ui.MainScreen
import com.rudra.moneynest.ui.addtransaction.AddTransactionScreen
import com.rudra.moneynest.ui.billtracker.BillTrackerScreen
import com.rudra.moneynest.ui.category.CategoryScreen
import com.rudra.moneynest.ui.dashboard.DashboardScreen
import com.rudra.moneynest.ui.export.DataExportScreen
import com.rudra.moneynest.ui.goals.GoalScreen
import com.rudra.moneynest.ui.monthlybudget.MonthlyBudgetScreen
import com.rudra.moneynest.ui.networth.NetWorthScreen
import com.rudra.moneynest.ui.report.ReportScreen
import com.rudra.moneynest.ui.security.PinScreen
import com.rudra.moneynest.ui.security.SecurityScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Security.route) {
        composable(Screen.Security.route) {
            SecurityScreen(navController = navController)
        }
        composable(Screen.Pin.route) {
            PinScreen(navController = navController)
        }
        composable(Screen.Main.route) {
            MainScreen(mainNavController = navController)
        }
        composable(Screen.Report.route) {
            ReportScreen()
        }
        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(onTransactionAdded = { navController.popBackStack() })
        }
        composable(Screen.Categories.route) {
            CategoryScreen()
        }
        composable(Screen.MonthlyBudget.route) {
            MonthlyBudgetScreen()
        }
        composable(Screen.Goals.route) {
            GoalScreen()
        }
        composable(Screen.BillTracker.route) {
            BillTrackerScreen()
        }
        composable(Screen.NetWorth.route) {
            NetWorthScreen()
        }
        composable(Screen.DataExport.route) {
            DataExportScreen()
        }
    }
}
