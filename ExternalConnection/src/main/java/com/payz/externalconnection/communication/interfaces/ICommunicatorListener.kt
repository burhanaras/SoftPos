package com.payz.externalconnection.communication.interfaces

interface ICommunicatorListener<T> {
    fun onMessageReceived(message: T, from: String)
    fun onConnected(connectedTo: String)
    fun onConnecting()
    fun onDisconnected()
}