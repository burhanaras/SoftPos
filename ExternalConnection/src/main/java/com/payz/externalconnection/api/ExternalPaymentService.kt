package com.payz.externalconnection.api

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.payz.externalconnection.data.model.Amount
import com.payz.externalconnection.data.model.Result
import com.payz.externalconnection.data.model.TransactionData
import com.payz.externalconnection.communication.CommClient
import com.payz.externalconnection.communication.EftPosProtocolClient
import com.payz.externalconnection.communication.bluetooth.BluetoothCommunicator
import com.payz.externalconnection.communication.interfaces.ICommClientListener
import com.payz.externalconnection.communication.protocol.EftPosProtocolJSons
import com.payz.externalconnection.data.DbContext
import com.payz.externalconnection.ui.devicelist.DeviceListActivity


object ExternalPaymentService {

    val TAG: String = ExternalPaymentService::class.java.simpleName

    private var context: Context? = null
    private lateinit var commClient: CommClient

    private lateinit var observer: ExternalPaymentServiceObserver
    private var saleCallBack: ((Result<TransactionData>) -> Unit)? = null
    private var refundCallBack: ((Result<TransactionData>) -> Unit)? = null

    fun init(context: Context) {
        ExternalPaymentService.context = context
    }

    fun discoverPosTerminalsOverBluetooth() {
        val intent = context?.let { DeviceListActivity.newIntent(it) }
        context?.startActivity(intent)
    }

    fun observe(observer: ExternalPaymentServiceObserver) {
        this.observer = observer
    }

    fun processSale(amount: Amount, callback: (Result<TransactionData>) -> Unit) {
        this.saleCallBack = callback
        DbContext.createNewSaleTransaction(amount)
        Handler().postDelayed(
            { callback(Result.Success(DbContext.transactionData)) },
            8000
        )
        if (!::commClient.isInitialized) {
            return
        }


        commClient.sendMessage(EftPosProtocolJSons.sale(amount, "com.paydustry.banking.nbademo"))

    }

    fun processRefund(transactionUUID: String, amount: Amount, callback: (Result<TransactionData>) -> Unit) {
        this.refundCallBack = callback
        DbContext.createNewRefundTransaction(transactionUUID, amount)
        Handler().postDelayed(
            { callback(Result.Success(DbContext.transactionData)) },
            8000
        )
        if (!::commClient.isInitialized) {
            return
        }
        commClient.sendMessage(EftPosProtocolJSons.refund(transactionUUID))
    }

    fun connect(data: Intent) {
        val bluetoothProtocolClient = EftPosProtocolClient()
        val bluetoothCommunicator = BluetoothCommunicator()
        bluetoothCommunicator.initialize()
        bluetoothCommunicator.start()
        bluetoothCommunicator.start(data, true)
        commClient = CommClient(bluetoothProtocolClient, bluetoothCommunicator)
        commClient.init()
        commClient.run(object : ICommClientListener {

            override fun onConnecting() {
                Log.d(TAG, "onConnecting() called")
            }

            override fun onConnectedTo(connectedTo: String) {
                Log.d(TAG, "onConnectedTo() called with: connectedTo = $connectedTo")
                observer.onConnectedTo(connectedTo)
            }

            override fun onDisconnectedFrom(disconnectedFrom: String) {
                observer.onDisconnectedFrom(disconnectedFrom)
            }

            override fun onMessageSent(messageSent: String, isSuccess: Boolean) {
                Log.d(
                    TAG,
                    "onMessageSent() called with: messageSent = $messageSent, isSuccess = $isSuccess"
                )
            }

            override fun onTransactionCompleted() {
                Log.d(TAG, "onPopUpRequest() called")
                when (DbContext.transactionData.transactionName) {
                    "SALE" -> {
                        saleCallBack?.let { it(Result.Success(DbContext.transactionData)) }
                    }
                    "REFUND" -> {
                        refundCallBack?.let { it(Result.Success(DbContext.transactionData)) }
                    }
                }
            }

            override fun onPrintCompleted() {
                Log.d(TAG, "onPrintCompleted() called")
            }

            override fun onReportZCompleted() {
                Log.d(TAG, "onReportZCompleted() called")
            }

        })
    }

}

interface ExternalPaymentServiceObserver {
    fun onConnectedTo(deviceName: String)
    fun onDisconnectedFrom(deviceName: String)
}