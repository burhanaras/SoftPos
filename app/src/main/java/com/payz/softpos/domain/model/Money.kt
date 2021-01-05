package com.payz.softpos.domain.model

import com.payz.externalconnection.data.model.Amount
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

class Money(
    private val currencyCode: String,
    private val currencySymbol: String,
    private val callback: (String) -> Unit
) {

    private var numbers: MutableList<Int> = mutableListOf()

    init {
        callback(toString())
    }

    fun add(number: Int) {
        when {
            number == 0 && numbers.isEmpty() -> {
                return
            }
            numbers.size >= 8 -> {
                return
            }
            else -> {
                numbers.add(number)
                callback(toString())
            }
        }
    }

    fun backSpace() {
        if (numbers.size == 0) return
        numbers.removeAt(numbers.size - 1)
        callback(toString())
    }

    fun toBigDecimal(): BigDecimal {
        val money: Double = when (numbers.size) {
            0 -> 0.0
            else -> numbers.joinToString(separator = "").toDouble() / 100
        }
        return BigDecimal(String.format(Locale.US, "%2f", money))
    }

    fun toAmount() = Amount(toBigDecimal(), currencyCode, currencySymbol)

    override fun toString(): String {
        return "$currencySymbol ${currencyFormat(toBigDecimal().toString())}"
    }

    private fun currencyFormat(amount: String): String {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }

    fun isZero(): Boolean {
        return toBigDecimal().compareTo(BigDecimal.ZERO) == 0
    }
}
