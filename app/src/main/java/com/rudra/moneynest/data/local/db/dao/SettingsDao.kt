package com.rudra.moneynest.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rudra.moneynest.data.local.db.entity.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Insert
    suspend fun insert(settings: Settings)

    @Update
    suspend fun update(settings: Settings)

    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<Settings>
}
