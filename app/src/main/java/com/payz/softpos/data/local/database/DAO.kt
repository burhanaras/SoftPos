package com.payz.softpos.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.payz.softpos.data.local.entity.TransactionEntity

@Dao
interface SoftPosDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM TransactionEntity")
    suspend fun getRefundableTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM TransactionEntity")
    suspend fun getAllTransactions(): List<TransactionEntity>

}