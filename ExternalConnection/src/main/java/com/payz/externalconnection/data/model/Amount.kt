package com.payz.externalconnection.data.model

import java.math.BigDecimal

data class Amount(
    val value: BigDecimal,
    val currencyCode: String,
    val currencySymbol: String
) {
    override fun toString(): String {
        return "$currencySymbol $value"
    }
}