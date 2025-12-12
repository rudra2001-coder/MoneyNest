package com.rudra.moneynest.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "net_worth_items")
data class NetWorthItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val amount: Double,
    val type: String // e.g., "Asset" or "Liability"
)
