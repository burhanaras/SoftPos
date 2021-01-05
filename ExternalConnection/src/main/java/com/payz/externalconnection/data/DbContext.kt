package com.payz.externalconnection.data

import com.payz.externalconnection.communication.model.applist.AppInfo
import com.payz.externalconnection.data.model.Amount
import com.payz.externalconnection.data.model.FinancialDirection
import com.payz.externalconnection.data.model.TransactionData
import java.util.*

object DbContext {

    lateinit var paymentApp: AppInfo
    lateinit var transactionData: TransactionData

    fun createNewSaleTransaction(amount: Amount) {
        transactionData = TransactionData(
            UUID.randomUUID().toString(),
            "SALE",
            FinancialDirection.POSITIVE,
            Date().toString(),
            amount,
            "Payment completed."
        )
    }

    fun createNewRefundTransaction(transactionUUID: String, amount: Amount) {
        transactionData = TransactionData(
            transactionUUID,
            "REFUND",
            FinancialDirection.NEGATIVE,
            Date().toString(),
            amount,
            "Refund completed!"
        )
    }
}