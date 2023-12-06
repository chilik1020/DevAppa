package com.chilik1020.devappa.model

import android.content.Context
import android.provider.Settings

data class ProxyPoint(
    val id: Int,
    val name: String,
    val ip: String,
    val port: Int
) {

    fun proxyAddress() = "$ip:$port"
}


val DISABLED_PROXY = ProxyPoint(id = 0, "Non proxy", "", 0)

fun ProxyPoint.setProxy(context: Context) {
    Settings.Global.putString(context.contentResolver, Settings.Global.HTTP_PROXY, proxyAddress())
}