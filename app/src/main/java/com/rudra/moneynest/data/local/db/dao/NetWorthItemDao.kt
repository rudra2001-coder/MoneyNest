package com.rudra.moneynest.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rudra.moneynest.data.local.db.entity.NetWorthItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NetWorthItemDao {
    @Insert
    suspend fun insert(netWorthItem: NetWorthItem)

    @Update
    suspend fun update(netWorthItem: NetWorthItem)

    @Delete
    suspend fun delete(netWorthItem: NetWorthItem)

    @Query("SELECT * FROM net_worth_items ORDER BY name ASC")
    fun getAllNetWorthItems(): Flow<List<NetWorthItem>>

    @Query("SELECT * FROM net_worth_items WHERE id = :id")
    fun getNetWorthItemById(id: Long): Flow<NetWorthItem>
}
