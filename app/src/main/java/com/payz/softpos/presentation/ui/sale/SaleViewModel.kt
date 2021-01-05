package com.payz.softpos.presentation.ui.sale

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payz.externalconnection.api.ExternalPaymentService
import com.payz.externalconnection.data.model.Result
import com.payz.softpos.domain.model.Money
import com.payz.softpos.domain.usecase.TransactionsUseCase
import com.payz.softpos.presentation.core.extension.toTransaction
import kotlinx.coroutines.launch

class SaleViewModel(private val transactionsUseCase: TransactionsUseCase) : ViewModel() {

    internal var displayedAmount: MutableLiveData<String> = MutableLiveData()
    internal var showProgressPopup: MutableLiveData<String> = MutableLiveData()
    internal var updateProgressPopup: MutableLiveData<String> = MutableLiveData()

    private val money = Money("AZN", "₼") {
        displayedAmount.postValue(it)
    }

    private fun sale() {
        showProgressPopup.postValue(money.toString())
        ExternalPaymentService.processSale(money.toAmount()) { result ->
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

    fun onClickNext() {
        if (!money.isZero()) {
            sale()
        }
    }

    fun onKeyPress(number: Int) {
        money.add(number)
    }

    fun onKeyPressComma() {
        money.add(0)
        money.add(0)
    }

    fun onKeyPressBackSpace() {
        money.backSpace()
    }
}