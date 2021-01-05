package com.payz.externalconnection.communication

import android.util.Log
import com.payz.externalconnection.communication.interfaces.ICommClientListener
import com.payz.externalconnection.communication.interfaces.ICommunicator
import com.payz.externalconnection.communication.interfaces.ICommunicatorListener
import com.payz.externalconnection.communication.interfaces.IProtocolClient

class CommClient(
    private var protocolClient: IProtocolClient,
    private var communicator: ICommunicator<String>
) {

    private lateinit var messageSentCallBack: (String, Boolean) -> Unit
    private var connectedDeviceName = ""

    fun init() {}

    fun run(listener: ICommClientListener) {
        this.messageSentCallBack = object : (String, Boolean) -> Unit {
            override fun invoke(p1: String, p2: Boolean) {
                Log.d(TAG, "Message sent: message = $p1, isSuccessful = $p2")
                listener.onMessageSent(p1, p2)
            }
        }

        communicator.addListener(object : ICommunicatorListener<String> {
            override fun onMessageReceived(message: String, from: String) {
                Log.d(TAG, "Message received: message = $message, from = $from")
                protocolClient.executeIncomingMessage(message, listener) { messageToSend ->
                    sendMessage(messageToSend)
                }
            }

            override fun onConnected(connectedTo: String) {
                Log.d(TAG, "Connected: connectedTo = $connectedTo")
                connectedDeviceName = connectedTo
                listener.onConnectedTo(connectedTo)
                protocolClient.onConnected { sendMessage(it) }
            }

            override fun onDisconnected() {
                Log.d(TAG, "Disconnected from $connectedDeviceName")
                connectedDeviceName = ""
                listener.onDisconnectedFrom(connectedDeviceName)
            }

            override fun onConnecting() {
                listener.onConnecting()
            }
        })
        communicator.start()

    }

    fun sendMessage(message: String) {
        communicator.write(message) {
            messageSentCallBack(message, it)
        }
    }

    fun destroy() {
        communicator.close()
    }

    fun isInitialized() = communicator.isConnected

    companion object {
        val TAG = CommClient::class.java.simpleName
    }
}