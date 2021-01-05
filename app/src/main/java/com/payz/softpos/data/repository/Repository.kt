package com.payz.softpos.data.repository

import com.payz.softpos.data.local.datasource.ILocalDataSource
import com.payz.softpos.data.local.entity.TransactionEntity

class Repository(private val localDataSource: ILocalDataSource) : IRepository {
    override suspend fun save(transactionEntity: TransactionEntity): Boolean {
        return localDataSource.save(transactionEntity)
    }

    override suspend fun getRefundableTransactions(): List<TransactionEntity> {
        return localDataSource.getRefundableTransactions()
    }

    override suspend fun getAllTransactions(): List<TransactionEntity> {
        return localDataSource.getAllTransactions()
    }
}