package com.payz.externalconnection.communication.interfaces

interface IProtocolClient {
    fun onConnected(callback: (String) -> Unit)
    fun executeIncomingMessage(message: String, commClientListener: ICommClientListener, sendMessageCallback: (String) -> Unit)
}