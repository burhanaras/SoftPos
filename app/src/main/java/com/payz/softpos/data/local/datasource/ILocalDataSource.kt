package com.payz.softpos.data.local.datasource

import com.payz.softpos.data.local.entity.TransactionEntity

interface ILocalDataSource {
    suspend fun save(transactionEntity: TransactionEntity): Boolean
    suspend fun getRefundableTransactions(): List<TransactionEntity>
    suspend fun getAllTransactions(): List<TransactionEntity>
}