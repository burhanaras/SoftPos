package com.payz.externalconnection.communication.protocol

import com.payz.externalconnection.data.model.Amount

object EftPosProtocolJSons {


    fun sale(amount: Amount, packageName: String) = "{\"type\":\"Sale\",\"packageName\":$packageName,\"amount\":{\"value\":${amount.value},\"currencyCode\":\"${amount.currencyCode}\"},\"onDevicePrintCustomerReceipts\":\"true\",\"onDevicePrintMerchantReceipts\":\"true\",\"onDevicePrintErrorReceipts\":\"true\"}"

    fun init() = "{\"type\":\"Init\"}"

    fun refund(transactionUUID: String) = "{\"type\":\"Refund\", \"transactionUUID\":$transactionUUID,\"onDevicePrintCustomerReceipts\":\"true\",\"onDevicePrintMerchantReceipts\":\"true\",\"onDevicePrintErrorReceipts\":\"true\"}"

    fun reverse(transactionUUID: String?) = "{\"type\":\"Reverse\",\"transactionUUID\":\"${transactionUUID}\"}"

    fun endOfDay() = "{\"type\":\"EndOfDay\",\"onDevicePrintEODReceipts\":true}"

    fun check(transactionUUID: String?) = "{\"type\":\"Check\",\"transactionUUID\":\"${transactionUUID}\"}"

    fun maintenance() = "{\"type\":\"Maintenance\"}"

    fun print(transactionUUID: String?) = "{\"type\":\"Print\",\"transactionUUID\":\"${transactionUUID}\",\"onDevicePrintCustomerReceipts\":\"true\",\"onDevicePrintMerchantReceipts\":\"true\",\"onDevicePrintErrorReceipts\":\"true\"}"

    fun ping() = "{\"type\":\"Ping\"}"

    fun appList() = "{\"type\":\"AppList\"}"

    fun ack(message: String?) = "{\"type\":\"ACK\", \"transactionUUID\":\"$message\"}"
}