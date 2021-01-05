package com.payz.softpos.data.local.datasource

import com.payz.softpos.data.local.entity.TransactionEntity

class FakeLocalDataSource : ILocalDataSource {

    private val data = mutableListOf<TransactionEntity>()

    override suspend fun save(transactionEntity: TransactionEntity): Boolean {
        data.add(transactionEntity)
        return true
    }

    override suspend fun getRefundableTransactions(): List<TransactionEntity> {
        return data
    }

    override suspend fun getAllTransactions(): List<TransactionEntity> {
        return data
    }
}