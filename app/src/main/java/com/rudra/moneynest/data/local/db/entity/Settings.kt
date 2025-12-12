package com.rudra.moneynest.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey
    val id: Int = 1, // Singleton settings object
    val currency: String,
    val theme: String,
    val passcode: String?
)
