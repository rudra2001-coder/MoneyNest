package com.rudra.moneynest.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Dashboard : Screen("dashboard")
    object Explorer : Screen("explorer")
    object Settings : Screen("settings")
    object Report : Screen("report")
    object AddTransaction : Screen("add_transaction")
    object Categories : Screen("categories")
    object MonthlyBudget : Screen("monthly_budget")
    object Goals : Screen("goals")
    object BillTracker : Screen("bill_tracker")
    object NetWorth : Screen("net_worth")
    object DataExport : Screen("data_export")
    object Security : Screen("security")
    object Pin : Screen("pin")
}
