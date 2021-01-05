package com.payz.externalconnection.communication.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.util.Log
import com.payz.externalconnection.communication.interfaces.ICommunicator
import com.payz.externalconnection.communication.interfaces.ICommunicatorListener
import com.payz.externalconnection.core.extension.isValidJson
import com.payz.externalconnection.ui.devicelist.DeviceListActivity

class BluetoothCommunicator() : ICommunicator<String> {

    private var mChatService: BluetoothChatService? = null
    private var listeners: MutableList<ICommunicatorListener<String>> = mutableListOf()
    private var mConnectedDeviceName: String = ""
    private lateinit var messageSentCallBack: (Boolean) -> Unit
    private var mBluetoothAdapter: BluetoothAdapter? = null


    override fun write(message: String, callback: (Boolean) -> Unit) {
        messageSentCallBack = callback

        if (message.isNotEmpty()) {
            // Get the message bytes and tell the BluetoothChatService to write
            val send = message.toByteArray()
            mChatService?.write(send)
        }
    }

    override fun addListener(listener: ICommunicatorListener<String>?) {
        listener?.let {
            listeners.clear()
            listeners.add(it)
        }
    }

    override fun close() {
        mChatService?.stop()
    }

    override fun start() {
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService?.mState == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService?.start()
            }
        }
    }


    /**
     * Connecting a device
     */
    override fun start(data: Intent, secure: Boolean) {
        // Get the device MAC address
        val extras = data.extras ?: return
        val address = extras.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS)
        // Get the BluetoothDevice object
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val device: BluetoothDevice = mBluetoothAdapter?.getRemoteDevice(address)!!
        // Attempt to connect to the device
        mChatService?.connect(device, secure)
    }

    override fun initialize() {
        if (mChatService == null) {
            mChatService = BluetoothChatService(mBluetoothHandler)
        }
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private val mBluetoothHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constants.MESSAGE_STATE_CHANGE -> when (msg.arg1) {
                    BluetoothChatService.STATE_CONNECTED -> {
                        listeners.forEach {
                            Log.i(TAG, "onConnected to $mConnectedDeviceName")
                            it.onConnected(mConnectedDeviceName)
                        }
                    }
                    BluetoothChatService.STATE_CONNECTING -> {
                        listeners.forEach {
                            Log.i(TAG, "onConnecting")
                            it.onConnecting()
                        }
                    }
                    BluetoothChatService.STATE_LISTEN, BluetoothChatService.STATE_NONE -> {
                        listeners.forEach {
                            Log.i(TAG, "onDisconnected")
                            it.onDisconnected()
                        }
                    }
                }

                Constants.MESSAGE_WRITE -> {
                    messageSentCallBack(true)
                }

                Constants.MESSAGE_READ -> {
                    val readBuf = msg.obj as ByteArray
                    // construct a string from the valid bytes in the buffer
                    var readMessage = String(readBuf, 0, msg.arg1)

                    if (!readMessage.isValidJson()) {
                        JSONMerger.input(readMessage)
                        if (JSONMerger.makesAnySense()) {
                            readMessage = JSONMerger.concat()
                            Log.d("JSON", "We have merged incoming JSON pieces.")
                        } else {
                            Log.d("JSON", "Invalid Json")
                            return
                        }
                    }

                    listeners.forEach {
                        Log.i(TAG, "onMessageRead: $readMessage")
                        it.onMessageReceived(readMessage, mConnectedDeviceName)
                    }

                }
                Constants.MESSAGE_DEVICE_NAME -> {
                    // save the connected device's name
                    mConnectedDeviceName = msg.data.getString(Constants.DEVICE_NAME).toString()

                }
                Constants.MESSAGE_TOAST -> {
                    Log.i(TAG, "onToastMessage")
                }
            }
        }
    }


    companion object {
        private val TAG: String = BluetoothCommunicator::class.java.simpleName
    }

    override val isConnected: Boolean
        get() {
            if (mChatService?.mState != BluetoothChatService.STATE_CONNECTED) return false
            return true
        }
}