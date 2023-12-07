package com.chilik1020.devappa.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.chilik1020.devappa.Proxies
import com.chilik1020.devappa.ProxyPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

interface ProtoProxyRepository {

    suspend fun getAvailableProxies(): Flow<Proxies>

    suspend fun add(point: ProxyPoint)

    suspend fun remove(point: ProxyPoint)

    suspend fun edit(point: ProxyPoint)
}

class ProtoDataStoreRepository(
    private val dataStore: DataStore<Proxies>
) : ProtoProxyRepository {

    override suspend fun getAvailableProxies(): Flow<Proxies> {
         return dataStore.data
             .catch { exception ->
                 // dataStore.data throws an IOException when an error is encountered when reading data
                 if (exception is IOException) {
                     Log.e("___)", "Error reading sort order preferences.", exception)
                     emit(Proxies.getDefaultInstance())
                 } else {
                     throw exception
                 }
             }
    }

    override suspend fun add(point: ProxyPoint) {
        dataStore.updateData { proxies ->
            if (proxies.toBuilder().pointsList.contains(point)) {
                proxies
            } else {
                proxies.toBuilder().addPoints(point).build()
            }
        }
    }

    override suspend fun remove(point: ProxyPoint) {
        dataStore.updateData { proxies ->
            if (proxies.toBuilder().pointsList.contains(point)) {
                val index = proxies.toBuilder().pointsList.indexOf(point)
                proxies.toBuilder().removePoints(index).build()
            } else {
                proxies
            }
        }
    }

    override suspend fun edit(point: ProxyPoint) {
        TODO("Not yet implemented")
    }

}