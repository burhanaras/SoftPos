package com.payz.softpos.domain.usecase

import com.payz.softpos.data.local.entity.TransactionEntity
import com.payz.softpos.data.repository.Repository
import com.payz.softpos.domain.model.Transaction
import com.payz.softpos.domain.model.fromEntity
import com.payz.softpos.domain.model.toEntity
import java.util.*

class TransactionsUseCase(private val repository: Repository) {

    suspend fun save(transaction: Transaction) {
        repository.save(transaction.toEntity())
    }

    suspend fun getRefundableTransactions(): List<TransactionEntity> {
        return repository.getRefundableTransactions()
    }

    suspend fun getAllTransactions(): List<TransactionEntity> {
        return repository.getAllTransactions()
    }

    fun getHistory(): List<Transaction> {

        val transactionEntities = mutableListOf<TransactionEntity>()
        repeat(15) { index ->
            val transaction = TransactionEntity(
                transactionUUID = UUID.randomUUID().toString(),
                transactionName = "\uD83D\uDCB5 Sale",
                transactionTime = "15:30",
                transactionAmount = 123.40 + index,
                currencyCode = "AZN"
            )
            transactionEntities.add(transaction)
        }

        var transaction = TransactionEntity(
            transactionUUID = UUID.randomUUID().toString(),
            transactionName = "\uD83D\uDD19 Refund",
            transactionTime = "15:30",
            transactionAmount = 123.40,
            currencyCode = "AZN"
        )
        transactionEntities.add(3, transaction)

        transaction = TransactionEntity(
            transactionUUID = UUID.randomUUID().toString(),
            transactionName = "\uD83D\uDD19 Refund",
            transactionTime = "15:30",
            transactionAmount = 123.40,
            currencyCode = "AZN"
        )
        transactionEntities.add(5, transaction)

        return transactionEntities.map { Transaction.fromEntity(it) }
    }
}