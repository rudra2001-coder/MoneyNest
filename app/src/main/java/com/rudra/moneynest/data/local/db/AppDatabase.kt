package com.rudra.moneynest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
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

@Database(
    entities = [
        Transaction::class,
        Category::class,
        MonthlyBudget::class,
        Goal::class,
        RecurringBill::class,
        NetWorthItem::class,
        Settings::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao
    abstract fun goalDao(): GoalDao
    abstract fun recurringBillDao(): RecurringBillDao
    abstract fun netWorthItemDao(): NetWorthItemDao
    abstract fun settingsDao(): SettingsDao
}
