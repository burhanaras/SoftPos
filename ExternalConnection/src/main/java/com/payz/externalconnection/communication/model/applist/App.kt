package com.payz.externalconnection.communication.model.applist

data class App(
    var packageName: String = "",
    var transactionDetailsList: List<TransactionDetails> = listOf()
)