package com.payz.externalconnection.data.model

interface PopUpType {
    companion object {
        const val TRANSACTION_COMPLETED = 0
        const val TRANSACTION_FAIL = 1
    }
}