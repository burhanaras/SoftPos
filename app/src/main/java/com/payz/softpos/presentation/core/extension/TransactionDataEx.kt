package com.payz.softpos.presentation.core.extension

import com.payz.externalconnection.data.model.TransactionData
import com.payz.softpos.domain.model.Transaction

fun TransactionData.toTransaction(): Transaction {
    return Transaction(
        transactionUUID = transactionUUID,
        transactionName = transactionName,
        transactionTime = transactionTime,
        transactionAmount = transactionAmount
    )
}