package com.payz.softpos.presentation.ui.main

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.payz.externalconnection.api.ExternalPaymentService
import com.payz.externalconnection.api.ExternalPaymentServiceObserver

class MainViewModel : ViewModel() {

    internal var status: MutableLiveData<String> = MutableLiveData()
    internal var enableBluetooth: MutableLiveData<Boolean> = MutableLiveData()

    init {
        ExternalPaymentService.observe(object : ExternalPaymentServiceObserver {
            override fun onConnectedTo(deviceName: String) {
                status.postValue("Connected to $deviceName")
            }

            override fun onDisconnectedFrom(deviceName: String) {
                status.postValue("You're not connected to POS")
            }
        })
    }

    fun discoverPosTerminals() {
        ExternalPaymentService.discoverPosTerminalsOverBluetooth()
    }

    fun connectBluetooth(data: Intent, secure: Boolean) {
        ExternalPaymentService.connect(data)
    }

}