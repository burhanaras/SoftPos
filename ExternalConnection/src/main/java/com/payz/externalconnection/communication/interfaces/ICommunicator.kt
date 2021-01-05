package com.payz.externalconnection.communication.interfaces

import android.content.Intent
import java.io.IOException

interface ICommunicator<T> {
    @Throws(IOException::class)
    fun write(message: T, callback: (Boolean) -> Unit)
    fun addListener(listener: ICommunicatorListener<T>?)
    fun close()
    fun start()
    fun start(data: Intent, secure: Boolean)
    fun initialize()
    val isConnected: Boolean
}
