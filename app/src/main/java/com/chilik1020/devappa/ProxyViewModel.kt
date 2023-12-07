package com.chilik1020.devappa

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chilik1020.devappa.model.proxyAddress
import com.chilik1020.devappa.data.ProtoProxyRepository
import com.chilik1020.devappa.model.DISABLED_PROTO_PROXY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProxyViewModel(
    private val protoProxyRepository: ProtoProxyRepository
) : ViewModel() {

    private val proxiesMutableState: MutableState<Proxies> =
        mutableStateOf(Proxies.getDefaultInstance())
    val proxiesState: State<Proxies> = proxiesMutableState

    private val currentProxyMutable: MutableState<String> = mutableStateOf(DISABLED_PROTO_PROXY.proxyAddress())
    val currentProxy: State<String> = currentProxyMutable

    init {
        loadProxies()
    }

    internal fun setCurrent(point: ProxyPoint) {
        currentProxyMutable.value = point.proxyAddress()
    }

    internal fun setCurrent(point: String) {
        currentProxyMutable.value = point
    }

    internal fun addPoint(name: String, ip: String, port: Int) {
        viewModelScope.launch {
            val nextId = nextId()
            protoProxyRepository.add(
                ProxyPoint.newBuilder()
                    .setId(nextId)
                    .setName(name)
                    .setIp(ip)
                    .setPort(port)
                    .build()
            )

            loadProxies()
        }
    }

    internal fun remove(point: ProxyPoint) {
        viewModelScope.launch {
            protoProxyRepository.remove(point)
            loadProxies()
        }
    }

    internal fun edit(point: ProxyPoint) {
        viewModelScope.launch {
            protoProxyRepository.edit(point)
            loadProxies()
        }
    }

    private fun loadProxies() {
        viewModelScope.launch {
            protoProxyRepository.getAvailableProxies().collectLatest {
                proxiesMutableState.value = it
                Log.d("___)", "Proto Proxies ${it.pointsList}")
            }
        }
    }

    private fun nextId(): Int {
        return proxiesState.value.pointsList.maxByOrNull { it.id }?.let { it.id + 1 } ?: 0
    }
}