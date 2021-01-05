package com.payz.softpos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey
    val transactionUUID: String,
    val transactionName: String,
    val transactionTime: String,
    val transactionAmount: Double,
    val currencyCode: String
)