package com.payz.softpos.presentation.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.payz.softpos.data.repository.Repository
import com.payz.softpos.domain.usecase.TransactionsUseCase
import com.payz.softpos.presentation.ui.history.HistoryViewModel
import com.payz.softpos.presentation.ui.refund.RefundViewModel
import com.payz.softpos.presentation.ui.sale.SaleViewModel
import java.lang.IllegalStateException

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(SaleViewModel::class.java) -> {
                    SaleViewModel(TransactionsUseCase(repository))
                }
                isAssignableFrom(RefundViewModel::class.java) -> {
                    RefundViewModel(TransactionsUseCase(repository))
                }
                isAssignableFrom(HistoryViewModel::class.java) -> {
                    HistoryViewModel(TransactionsUseCase(repository))
                }
                else -> throw IllegalStateException("You have to define your ViewModel class in ViewModelProvider!")
            }
        } as T
}