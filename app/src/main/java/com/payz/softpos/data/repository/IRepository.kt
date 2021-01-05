package com.payz.softpos.data.repository

import com.payz.softpos.data.local.entity.TransactionEntity

interface IRepository {
    suspend fun save(transactionEntity: TransactionEntity): Boolean
    suspend fun getRefundableTransactions(): List<TransactionEntity>
    suspend fun getAllTransactions(): List<TransactionEntity>
}