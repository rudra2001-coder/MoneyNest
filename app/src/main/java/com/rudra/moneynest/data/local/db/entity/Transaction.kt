package com.rudra.moneynest.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val type: String, // "Income" or "Expense"
    val category: String,
    val date: Long,
    val notes: String?,
    val receiptPath: String?
)
