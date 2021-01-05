package com.payz.externalconnection.communication.bluetooth

import android.os.Handler
import android.util.Log
import com.payz.externalconnection.core.extension.isValidJson
import com.payz.externalconnection.core.extension.toJson


object JSONMerger {
    private const val TAG = "JSONMerger"
    private var jSons = mutableListOf<String>()
    private var handler = Handler()

    @Synchronized
    fun input(readMessage: String) {
        jSons.add(readMessage)
        handler.postDelayed({ jSons.clear() }, 1000)
    }

    fun makesAnySense(): Boolean {
        var concat = ""
        jSons.map { concat += it }

        return concat.isValidJson()
    }

    fun concat(): String {
        var concat = ""
        jSons.map { concat += it }
        val response = concat.toJson()
        jSons.clear()
        return response

    }

    fun concatWithOutJsonValidation(): String {
        var response = ""
        jSons.map { response += it }
        jSons.clear()
        return response

    }

    fun logLargeString(str: String) {
        if (str.length > 3000) {
            Log.i(TAG, str.substring(0, 3000))
            logLargeString(str.substring(3000))
        } else {
            Log.i(TAG, str) // continuation
        }
    }
}