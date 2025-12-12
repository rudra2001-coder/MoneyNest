package com.rudra.moneynest.data

import com.rudra.moneynest.data.local.LocalDataSource
import com.rudra.moneynest.data.local.db.entity.Category
import com.rudra.moneynest.data.local.db.entity.Goal
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import com.rudra.moneynest.data.local.db.entity.NetWorthItem
import com.rudra.moneynest.data.local.db.entity.RecurringBill
import com.rudra.moneynest.data.local.db.entity.Settings
import com.rudra.moneynest.data.local.db.entity.Transaction
import com.rudra.moneynest.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    // Transactions
    override fun getAllTransactions(): Flow<List<Transaction>> = localDataSource.getAllTransactions()
    override fun getTransactionById(id: Long): Flow<Transaction> = localDataSource.getTransactionById(id)
    override suspend fun insertTransaction(transaction: Transaction) = localDataSource.insertTransaction(transaction)
    override suspend fun updateTransaction(transaction: Transaction) = localDataSource.updateTransaction(transaction)
    override suspend fun deleteTransaction(transaction: Transaction) = localDataSource.deleteTransaction(transaction)

    // Categories
    override fun getAllCategories(): Flow<List<Category>> = localDataSource.getAllCategories()
    override fun getCategoryById(id: Long): Flow<Category> = localDataSource.getCategoryById(id)
    override suspend fun insertCategory(category: Category) = localDataSource.insertCategory(category)
    override suspend fun updateCategory(category: Category) = localDataSource.updateCategory(category)
    override suspend fun deleteCategory(category: Category) = localDataSource.deleteCategory(category)

    // Monthly Budgets
    override fun getMonthlyBudget(month: String): Flow<MonthlyBudget> = localDataSource.getMonthlyBudget(month)
    override suspend fun insertMonthlyBudget(monthlyBudget: MonthlyBudget) = localDataSource.insertMonthlyBudget(monthlyBudget)
    override suspend fun updateMonthlyBudget(monthlyBudget: MonthlyBudget) = localDataSource.updateMonthlyBudget(monthlyBudget)

    // Goals
    override fun getAllGoals(): Flow<List<Goal>> = localDataSource.getAllGoals()
    override fun getGoalById(id: Long): Flow<Goal> = localDataSource.getGoalById(id)
    override suspend fun insertGoal(goal: Goal) = localDataSource.insertGoal(goal)
    override suspend fun updateGoal(goal: Goal) = localDataSource.updateGoal(goal)
    override suspend fun deleteGoal(goal: Goal) = localDataSource.deleteGoal(goal)

    // Recurring Bills
    override fun getAllRecurringBills(): Flow<List<RecurringBill>> = localDataSource.getAllRecurringBills()
    override fun getRecurringBillById(id: Long): Flow<RecurringBill> = localDataSource.getRecurringBillById(id)
    override suspend fun insertRecurringBill(recurringBill: RecurringBill) = localDataSource.insertRecurringBill(recurringBill)
    override suspend fun updateRecurringBill(recurringBill: RecurringBill) = localDataSource.updateRecurringBill(recurringBill)
    override suspend fun deleteRecurringBill(recurringBill: RecurringBill) = localDataSource.deleteRecurringBill(recurringBill)

    // Net Worth Items
    override fun getAllNetWorthItems(): Flow<List<NetWorthItem>> = localDataSource.getAllNetWorthItems()
    override fun getNetWorthItemById(id: Long): Flow<NetWorthItem> = localDataSource.getNetWorthItemById(id)
    override suspend fun insertNetWorthItem(netWorthItem: NetWorthItem) = localDataSource.insertNetWorthItem(netWorthItem)
    override suspend fun updateNetWorthItem(netWorthItem: NetWorthItem) = localDataSource.updateNetWorthItem(netWorthItem)
    override suspend fun deleteNetWorthItem(netWorthItem: NetWorthItem) = localDataSource.deleteNetWorthItem(netWorthItem)

    // Settings
    override fun getSettings(): Flow<Settings> = localDataSource.getSettings()
    override suspend fun insertSettings(settings: Settings) = localDataSource.insertSettings(settings)
    override suspend fun updateSettings(settings: Settings) = localDataSource.updateSettings(settings)
}
