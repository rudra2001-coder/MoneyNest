package com.rudra.moneynest.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rudra.moneynest.data.local.db.entity.MonthlyBudget
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlyBudgetDao {
    @Insert
    suspend fun insert(monthlyBudget: MonthlyBudget)

    @Update
    suspend fun update(monthlyBudget: MonthlyBudget)

    @Query("SELECT * FROM monthly_budgets WHERE month = :month")
    fun getMonthlyBudget(month: String): Flow<MonthlyBudget>
}
