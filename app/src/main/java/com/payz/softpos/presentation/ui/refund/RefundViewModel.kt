package com.payz.softpos.presentation.ui.refund

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payz.externalconnection.api.ExternalPaymentService
import com.payz.externalconnection.data.model.Result
import com.payz.softpos.domain.model.Transaction
import com.payz.softpos.domain.usecase.TransactionsUseCase
import com.payz.softpos.presentation.core.extension.toTransaction
import kotlinx.coroutines.launch

class RefundViewModel(private val transactionsUseCase: TransactionsUseCase) : ViewModel() {

    var transactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    internal var showProgressPopup: MutableLiveData<String> = MutableLiveData()
    internal var updateProgressPopup: MutableLiveData<String> = MutableLiveData()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        transactions.postValue(transactionsUseCase.getHistory())
    }

    fun refund(transaction: Transaction) {
        showProgressPopup.postValue(transaction.transactionAmount.toString())
        ExternalPaymentService.processRefund(
            transaction.transactionUUID,
            transaction.transactionAmount
        ) { result ->
            when (result) {
                is Result.Success -> {
                    updateProgressPopup.postValue(result.data.transactionResult)
                    viewModelScope.launch {
                        transactionsUseCase.save(result.data.toTransaction())
                    }
                }
                is Result.Error -> {
                    result.exception
                    updateProgressPopup.postValue(result.data?.transactionResult)
                    viewModelScope.launch {
                        result.data?.toTransaction()?.let { transactionsUseCase.save(it) }
                    }
                }
            }
        }
    }
}