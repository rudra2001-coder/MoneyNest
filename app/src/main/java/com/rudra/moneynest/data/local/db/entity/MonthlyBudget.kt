package com.rudra.moneynest.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthly_budgets")
data class MonthlyBudget(
    @PrimaryKey
    val month: String, // e.g., "2024-10"
    val plannedAmount: Double,
    val spentAmount: Double,
    val remainingAmount: Double
)
