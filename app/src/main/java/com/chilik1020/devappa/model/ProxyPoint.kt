package com.chilik1020.devappa.model

import android.content.Context
import android.provider.Settings


val DISABLED_PROTO_PROXY: com.chilik1020.devappa.ProxyPoint = com.chilik1020.devappa.ProxyPoint.newBuilder()
    .setId(0)
    .setName("Non proxy")
    .setIp("")
    .setPort(0)
    .build()

fun com.chilik1020.devappa.ProxyPoint.setProxy(context: Context) {
    Settings.Global.putString(context.contentResolver, Settings.Global.HTTP_PROXY, "$ip:$port")
}

fun com.chilik1020.devappa.ProxyPoint.proxyAddress() = "$ip:$port"