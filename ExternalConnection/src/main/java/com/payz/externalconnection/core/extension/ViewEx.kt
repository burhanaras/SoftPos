package com.payz.externalconnection.core.extension

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException


fun String.toJson(): String {
    return try {
        GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(this))
    } catch (e: Exception) {
        this
    }
}

fun String.isValidJson(): Boolean {
    val gSon = Gson()
    return try {
        val jSonObjectType = gSon.fromJson(this, Any::class.java).javaClass
        jSonObjectType != String::class.java

    } catch (ex: JsonSyntaxException) {
        false
    } catch (exx: Exception){
        false
    }
}