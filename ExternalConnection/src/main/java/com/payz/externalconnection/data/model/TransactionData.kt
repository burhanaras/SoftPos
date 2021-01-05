package com.payz.externalconnection.data.model

data class TransactionData(
    var transactionUUID: String,
    var transactionName: String,
    var financialDirection: FinancialDirection,
    var transactionTime: String,
    var transactionAmount: Amount,
    var transactionResult: String
)