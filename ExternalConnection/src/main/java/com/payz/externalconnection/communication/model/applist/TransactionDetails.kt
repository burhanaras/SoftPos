package com.payz.externalconnection.communication.model.applist

data class TransactionDetails(
    var amountNeeded: Boolean = false,
    var financialDirection: String = "",
    var supported: Boolean = false,
    var transactionName: String = "",
    var transactionType: String = "",
    var transactionWorkflowName: String = ""
)