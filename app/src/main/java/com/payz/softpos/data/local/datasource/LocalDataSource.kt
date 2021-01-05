package com.payz.softpos.data.local.datasource

import com.payz.softpos.data.local.database.SoftPosDAO
import com.payz.softpos.data.local.entity.TransactionEntity

class LocalDataSource(private val dao: SoftPosDAO) : ILocalDataSource {
    override suspend fun save(transactionEntity: TransactionEntity): Boolean {
        return try {
            dao.save(transactionEntity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getRefundableTransactions(): List<TransactionEntity> {
        return dao.getRefundableTransactions()
    }

    override suspend fun getAllTransactions(): List<TransactionEntity> {
        return dao.getAllTransactions()
    }
}