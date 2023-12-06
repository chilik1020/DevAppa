package com.chilik1020.devappa.repository

import com.chilik1020.devappa.model.DISABLED_PROXY
import com.chilik1020.devappa.model.ProxyPoint

class ProxyRepositoryLocal : ProxyRepository {

    private val proxies = mutableListOf<ProxyPoint>()

    init {
        with(proxies) {
            add(DISABLED_PROXY)
            add(ProxyPoint(1, "MacBook M1", "192.168.100.18", 8080))
            add(ProxyPoint(2, "Pixal 7", "192.168.204.97", 8080))
            add(ProxyPoint(2, "MacBook M1 Home Proxyman", "192.168.222.97", 9090))
        }
    }

    override fun getAvailableProxies(): List<ProxyPoint> = proxies.toList()

    override fun add(point: ProxyPoint) {
        proxies.add(point)
    }

    override fun remove(point: ProxyPoint) {
        proxies.removeIf { it.id == point.id }
    }

    override fun edit(point: ProxyPoint) {
        proxies.find { it.id == point.id }?.let {
            val index = proxies.indexOf(it)
            proxies.set(index, point)
        }
    }
}