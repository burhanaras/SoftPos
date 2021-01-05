package com.payz.softpos.presentation.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payz.softpos.domain.model.Transaction
import com.payz.softpos.domain.usecase.TransactionsUseCase

class HistoryViewModel(private val transactionsUseCase: TransactionsUseCase) : ViewModel() {

    var transactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        transactions.postValue(transactionsUseCase.getHistory())
    }

}