package com.chilik1020.devappa.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.chilik1020.devappa.Proxies
import java.io.InputStream
import java.io.OutputStream

object ProxiesSerializer : Serializer<Proxies> {
    override val defaultValue: Proxies = Proxies.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): Proxies {
        try {
            return Proxies.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        } catch (e: java.io.IOException) {
            e.printStackTrace()
            throw e
        }
    }
    override suspend fun writeTo(t: Proxies, output: OutputStream) = t.writeTo(output)
}

val Context.proxyPointsDataStore: DataStore<Proxies> by dataStore(
    fileName = "ProxyPoints.pb",
    serializer = ProxiesSerializer
)