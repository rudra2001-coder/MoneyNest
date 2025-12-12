package com.rudra.moneynest.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.moneynest.data.Repository
import com.rudra.moneynest.data.local.db.entity.Category
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import com.rudra.moneynest.data.local.db.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

data class BudgetProgress(val categoryName: String, val percentage: Float, val spent: Double, val limit: Double)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _recentTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val recentTransactions: StateFlow<List<Transaction>> = _recentTransactions.asStateFlow()

    private val _spendingByCategory = MutableStateFlow<Map<String, Double>>(emptyMap())
    val spendingByCategory: StateFlow<Map<String, Double>> = _spendingByCategory.asStateFlow()

    private val _monthlyBudget = MutableStateFlow<MonthlyBudget?>(null)
    val monthlyBudget: StateFlow<MonthlyBudget?> = _monthlyBudget.asStateFlow()

    private val _budgetProgress = MutableStateFlow<List<BudgetProgress>>(emptyList())
    val budgetProgress: StateFlow<List<BudgetProgress>> = _budgetProgress.asStateFlow()
    
    private val currentMonth: String
        get() = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

    init {
        val transactionsFlow = repository.getAllTransactions()
        val categoriesFlow = repository.getAllCategories()
        val monthlyBudgetFlow = repository.getMonthlyBudget(currentMonth)

        // Combine flows to calculate all necessary data
        combine(transactionsFlow, categoriesFlow, monthlyBudgetFlow) { transactions, categories, budget ->
            // Recent Transactions
            _recentTransactions.value = transactions.take(5)

            // Spending by category
            val spendingMap = transactions
                .filter { it.type == "Expense" }
                .groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }
            _spendingByCategory.value = spendingMap

            // Monthly Budget and Savings
            val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.amount }
            val totalExpense = spendingMap.values.sum()
            val savings = totalIncome - totalExpense
            val updatedBudget = (budget ?: MonthlyBudget(currentMonth, 0.0, 0.0, 0.0)).copy(
                spentAmount = totalExpense,
                remainingAmount = (budget?.plannedAmount ?: 0.0) - totalExpense
            )
            _monthlyBudget.value = updatedBudget

            // Budget Progress Bars
            _budgetProgress.value = categories.map { category ->
                val spent = spendingMap[category.name] ?: 0.0
                val limit = category.budgetLimit
                val percentage = if (limit > 0) (spent / limit).toFloat() else 0f
                BudgetProgress(category.name, percentage, spent, limit)
            }.filter { it.limit > 0 } // Only show categories with a budget

        }.launchIn(viewModelScope)
    }
}