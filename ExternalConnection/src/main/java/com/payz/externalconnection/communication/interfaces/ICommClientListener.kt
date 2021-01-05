package com.payz.externalconnection.communication.interfaces

interface ICommClientListener {
    fun onConnecting()
    fun onConnectedTo(connectedTo: String)
    fun onDisconnectedFrom(disconnectedFrom: String)
    fun onMessageSent(messageSent: String, isSuccess: Boolean)
    fun onTransactionCompleted()
    fun onPrintCompleted()
    fun onReportZCompleted()

}