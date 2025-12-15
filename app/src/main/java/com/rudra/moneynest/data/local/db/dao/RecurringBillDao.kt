package com.rudra.moneynest.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rudra.moneynest.data.local.db.entity.RecurringBill
import kotlinx.coroutines.flow.Flow

@Dao
interface RecurringBillDao {
    @Insert
    suspend fun insert(recurringBill: RecurringBill)

    @Update
    suspend fun update(recurringBill: RecurringBill)

    @Delete
    suspend fun delete(recurringBill: RecurringBill)

    @Query("SELECT * FROM recurring_bills ORDER BY dueDate ASC")
    fun getAllRecurringBills(): Flow<List<RecurringBill>>

    @Query("SELECT * FROM recurring_bills WHERE id = :id")
    fun getRecurringBillById(id: Long): Flow<RecurringBill>

    @Query("DELETE FROM recurring_bills")
    suspend fun clear()
}
