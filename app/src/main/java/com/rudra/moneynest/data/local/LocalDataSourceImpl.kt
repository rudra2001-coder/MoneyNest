package com.rudra.moneynest.data.local

import com.rudra.moneynest.data.local.db.dao.CategoryDao
import com.rudra.moneynest.data.local.db.dao.GoalDao
import com.rudra.moneynest.data.local.db.dao.MonthlyBudgetDao
import com.rudra.moneynest.data.local.db.dao.NetWorthItemDao
import com.rudra.moneynest.data.local.db.dao.RecurringBillDao
import com.rudra.moneynest.data.local.db.dao.SettingsDao
import com.rudra.moneynest.data.local.db.dao.TransactionDao
import com.rudra.moneynest.data.local.db.entity.Category
import com.rudra.moneynest.data.local.db.entity.Goal
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import com.rudra.moneynest.data.local.db.entity.NetWorthItem
import com.rudra.moneynest.data.local.db.entity.RecurringBill
import com.rudra.moneynest.data.local.db.entity.Settings
import com.rudra.moneynest.data.local.db.entity.Transaction
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao,
    private val monthlyBudgetDao: MonthlyBudgetDao,
    private val goalDao: GoalDao,
    private val recurringBillDao: RecurringBillDao,
    private val netWorthItemDao: NetWorthItemDao,
    private val settingsDao: SettingsDao
) : LocalDataSource {

    // Transactions
    override fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()
    override fun getTransactionById(id: Long): Flow<Transaction> = transactionDao.getTransactionById(id)
    override suspend fun insertTransaction(transaction: Transaction) = transactionDao.insert(transaction)
    override suspend fun updateTransaction(transaction: Transaction) = transactionDao.update(transaction)
    override suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)

    // Categories
    override fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()
    override fun getCategoryById(id: Long): Flow<Category> = categoryDao.getCategoryById(id)
    override suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    override suspend fun updateCategory(category: Category) = categoryDao.update(category)
    override suspend fun deleteCategory(category: Category) = categoryDao.delete(category)

    // Monthly Budgets
    override fun getMonthlyBudget(month: String): Flow<MonthlyBudget> = monthlyBudgetDao.getMonthlyBudget(month)
    override suspend fun insertMonthlyBudget(monthlyBudget: MonthlyBudget) = monthlyBudgetDao.insert(monthlyBudget)
    override suspend fun updateMonthlyBudget(monthlyBudget: MonthlyBudget) = monthlyBudgetDao.update(monthlyBudget)

    // Goals
    override fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()
    override fun getGoalById(id: Long): Flow<Goal> = goalDao.getGoalById(id)
    override suspend fun insertGoal(goal: Goal) = goalDao.insert(goal)
    override suspend fun updateGoal(goal: Goal) = goalDao.update(goal)
    override suspend fun deleteGoal(goal: Goal) = goalDao.delete(goal)

    // Recurring Bills
    override fun getAllRecurringBills(): Flow<List<RecurringBill>> = recurringBillDao.getAllRecurringBills()
    override fun getRecurringBillById(id: Long): Flow<RecurringBill> = recurringBillDao.getRecurringBillById(id)
    override suspend fun insertRecurringBill(recurringBill: RecurringBill) = recurringBillDao.insert(recurringBill)
    override suspend fun updateRecurringBill(recurringBill: RecurringBill) = recurringBillDao.update(recurringBill)
    override suspend fun deleteRecurringBill(recurringBill: RecurringBill) = recurringBillDao.delete(recurringBill)

    // Net Worth Items
    override fun getAllNetWorthItems(): Flow<List<NetWorthItem>> = netWorthItemDao.getAllNetWorthItems()
    override fun getNetWorthItemById(id: Long): Flow<NetWorthItem> = netWorthItemDao.getNetWorthItemById(id)
    override suspend fun insertNetWorthItem(netWorthItem: NetWorthItem) = netWorthItemDao.insert(netWorthItem)
    override suspend fun updateNetWorthItem(netWorthItem: NetWorthItem) = netWorthItemDao.update(netWorthItem)
    override suspend fun deleteNetWorthItem(netWorthItem: NetWorthItem) = netWorthItemDao.delete(netWorthItem)

    // Settings
    override fun getSettings(): Flow<Settings> = settingsDao.getSettings()
    override suspend fun insertSettings(settings: Settings) = settingsDao.insert(settings)
    override suspend fun updateSettings(settings: Settings) = settingsDao.update(settings)

    override suspend fun clearAllTables() {
        transactionDao.clear()
        categoryDao.clear()
        monthlyBudgetDao.clear()
        goalDao.clear()
        recurringBillDao.clear()
        netWorthItemDao.clear()
        settingsDao.clear()
    }
}
