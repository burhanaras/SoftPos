package com.payz.externalconnection.communication.model

import androidx.annotation.Nullable


data class SaleResponse(
    val type: String = "SaleResponse",
    val isSuccessful: Boolean,
    @Nullable val transactionUUID: String?,
    @Nullable val paymentCustomerReceipts: List<String>?,
    @Nullable val paymentMerchantReceipts: List<String>?,
    @Nullable val paymentErrorReceipts: List<String>?
) : BaseResponse()