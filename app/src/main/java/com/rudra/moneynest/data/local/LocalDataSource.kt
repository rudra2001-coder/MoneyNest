package com.rudra.moneynest.data.local

import com.rudra.moneynest.data.local.db.entity.Category
import com.rudra.moneynest.data.local.db.entity.Goal
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import com.rudra.moneynest.data.local.db.entity.NetWorthItem
import com.rudra.moneynest.data.local.db.entity.RecurringBill
import com.rudra.moneynest.data.local.db.entity.Settings
import com.rudra.moneynest.data.local.db.entity.Transaction
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    // Transactions
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getTransactionById(id: Long): Flow<Transaction>
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)

    // Categories
    fun getAllCategories(): Flow<List<Category>>
    fun getCategoryById(id: Long): Flow<Category>
    suspend fun insertCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)

    // Monthly Budgets
    fun getMonthlyBudget(month: String): Flow<MonthlyBudget>
    suspend fun insertMonthlyBudget(monthlyBudget: MonthlyBudget)
    suspend fun updateMonthlyBudget(monthlyBudget: MonthlyBudget)

    // Goals
    fun getAllGoals(): Flow<List<Goal>>
    fun getGoalById(id: Long): Flow<Goal>
    suspend fun insertGoal(goal: Goal)
    suspend fun updateGoal(goal: Goal)
    suspend fun deleteGoal(goal: Goal)

    // Recurring Bills
    fun getAllRecurringBills(): Flow<List<RecurringBill>>
    fun getRecurringBillById(id: Long): Flow<RecurringBill>
    suspend fun insertRecurringBill(recurringBill: RecurringBill)
    suspend fun updateRecurringBill(recurringBill: RecurringBill)
    suspend fun deleteRecurringBill(recurringBill: RecurringBill)

    // Net Worth Items
    fun getAllNetWorthItems(): Flow<List<NetWorthItem>>
    fun getNetWorthItemById(id: Long): Flow<NetWorthItem>
    suspend fun insertNetWorthItem(netWorthItem: NetWorthItem)
    suspend fun updateNetWorthItem(netWorthItem: NetWorthItem)
    suspend fun deleteNetWorthItem(netWorthItem: NetWorthItem)

    // Settings
    fun getSettings(): Flow<Settings>
    suspend fun insertSettings(settings: Settings)
    suspend fun updateSettings(settings: Settings)

    suspend fun clearAllTables()
}
