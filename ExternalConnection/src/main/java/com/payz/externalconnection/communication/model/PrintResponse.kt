package com.payz.externalconnection.communication.model

import androidx.annotation.Nullable

data class PrintResponse(
    val type: String = "PrintResponse",
    @Nullable val transactionUUID: String?,
    @Nullable val paymentCustomerReceipts: List<String>? = null,
    @Nullable val paymentMerchantReceipts: List<String>? = null,
    @Nullable val paymentErrorReceipts: List<String>? = null
) : BaseResponse()