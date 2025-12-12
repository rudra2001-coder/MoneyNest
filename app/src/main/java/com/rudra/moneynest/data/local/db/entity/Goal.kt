package com.rudra.moneynest.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val goalAmount: Double,
    val savedAmount: Double,
    val deadline: Long
)
