package com.payz.externalconnection.data.model

enum class FinancialDirection(val value: Int) {
    NEUTRE(0),
    POSITIVE(1),
    NEGATIVE(-1)
}