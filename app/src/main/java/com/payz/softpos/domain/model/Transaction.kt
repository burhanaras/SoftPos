package com.payz.softpos.domain.model

import com.payz.externalconnection.data.model.Amount
import com.payz.softpos.data.local.entity.TransactionEntity
import java.math.BigDecimal

data class Transaction(
    val transactionUUID: String,
    val transactionName: String,
    val transactionTime: String,
    val transactionAmount: Amount
) {
    companion object
}

fun Transaction.toEntity() = TransactionEntity(
    transactionUUID = transactionUUID,
    transactionName = transactionName,
    transactionTime = transactionTime,
    transactionAmount = transactionAmount.value.toDouble(),
    currencyCode = transactionAmount.currencyCode
)

fun Transaction.Companion.fromEntity(entity: TransactionEntity) = Transaction(
    transactionUUID = entity.transactionUUID,
    transactionName = entity.transactionName,
    transactionTime = entity.transactionTime,
    transactionAmount = Amount(
        BigDecimal.valueOf(entity.transactionAmount),
        entity.currencyCode,
        "₼"
    )
)