package com.chilik1020.devappa

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.chilik1020.devappa.model.DISABLED_PROXY
import com.chilik1020.devappa.model.ProxyPoint
import com.chilik1020.devappa.repository.ProxyRepository

class ProxyViewModel(private val proxyRepository: ProxyRepository) : ViewModel() {

    private val proxiesMutableState: MutableState<List<ProxyPoint>> = mutableStateOf(emptyList())
    val proxiesState: State<List<ProxyPoint>> = proxiesMutableState

//    private val currentProxyMutable: MutableState<ProxyPoint> = mutableStateOf(DISABLED_PROXY)
//    val currentProxy: State<ProxyPoint> = currentProxyMutable

    private val currentProxyMutable: MutableState<String> =
        mutableStateOf(DISABLED_PROXY.proxyAddress())
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
        proxyRepository.add(ProxyPoint(nextId(), name, ip, port))
        loadProxies()
    }

    private fun loadProxies() {
        proxiesMutableState.value = proxyRepository.getAvailableProxies()
    }


    private fun nextId(): Int {
        return proxiesState.value.maxByOrNull { it.id }?.let { it.id + 1 } ?: 0
    }
}