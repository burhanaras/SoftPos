package com.payz.externalconnection.communication.model

import androidx.annotation.Nullable

data class RefundResponse(
    val type: String = "RefundResponse",
    val isSuccessful: Boolean,
    val transactionUUID: String,
    @Nullable val paymentCustomerReceipts: List<String>?,
    @Nullable val paymentMerchantReceipts: List<String>?,
    @Nullable val paymentErrorReceipts: List<String>?
) : BaseResponse()