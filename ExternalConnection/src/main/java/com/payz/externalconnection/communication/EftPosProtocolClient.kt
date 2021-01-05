package com.payz.externalconnection.communication

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.payz.externalconnection.communication.interfaces.ICommClientListener
import com.payz.externalconnection.communication.interfaces.IProtocolClient
import com.payz.externalconnection.communication.model.GenericECRCommand
import com.payz.externalconnection.communication.model.PrintResponse
import com.payz.externalconnection.communication.model.RefundResponse
import com.payz.externalconnection.communication.model.SaleResponse
import com.payz.externalconnection.communication.model.applist.AppListResponse
import com.payz.externalconnection.communication.protocol.EftPosProtocolJSons
import com.payz.externalconnection.data.DbContext

class EftPosProtocolClient : IProtocolClient {

    private val gSon = Gson()

    override fun onConnected(callback: (String) -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ callback(EftPosProtocolJSons.init()) }, 1000)
        handler.postDelayed({ callback(EftPosProtocolJSons.appList()) }, 2000)
    }

    override fun executeIncomingMessage(
        message: String,
        commClientListener: ICommClientListener,
        sendMessageCallback: (String) -> Unit
    ) {
        handleReceivedMessage(message, commClientListener, sendMessageCallback)
    }

    private fun handleReceivedMessage(
        jsonData: String,
        commClientListener: ICommClientListener,
        sendMessageCallback: (String) -> Unit
    ) {
        val genericCommand = Gson().fromJson(jsonData, GenericECRCommand::class.java)
        when (genericCommand.type) {
            "PrintResponse" -> {
                val printResponse = gSon.fromJson(jsonData, PrintResponse::class.java)
                sendMessageCallback(ack(printResponse.transactionUUID))
                commClientListener.onPrintCompleted()
            }
            "RefundResponse" -> {
                val refundResponse = gSon.fromJson(jsonData, RefundResponse::class.java)
                DbContext.transactionData.transactionUUID = refundResponse.transactionUUID
                sendMessageCallback(ack(refundResponse.transactionUUID))
                commClientListener.onTransactionCompleted()
            }
            "SaleResponse" -> {
                val saleResponse = gSon.fromJson(jsonData, SaleResponse::class.java)
                sendMessageCallback(ack(saleResponse.transactionUUID))
                DbContext.transactionData.transactionUUID = saleResponse.transactionUUID.toString()
                commClientListener.onTransactionCompleted()
            }
            "AppListResponse" -> {
                val appListResponse = Gson().fromJson(jsonData, AppListResponse::class.java)
                Log.d(
                    TAG,
                    "AppListResponse() called with: ${appListResponse.appList.size} apps"
                )
                appListResponse.appList.map { Log.d(TAG, it.app.packageName) }
                //TODO: Select payment app and save to DbContext
                if (appListResponse.appList.isNotEmpty()) {
                    DbContext.paymentApp = appListResponse.appList[0]
                }
            }
            //other response types && operations
        }
    }

    private fun ack(transactionUUID: String?) =
        "{\"type\":\"ACK\", \"transactionUUID\":\"$transactionUUID\"}"

    companion object {
        val TAG: String = EftPosProtocolClient::class.java.simpleName
    }
}