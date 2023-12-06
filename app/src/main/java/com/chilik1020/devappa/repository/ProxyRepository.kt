package com.chilik1020.devappa.repository

import com.chilik1020.devappa.model.ProxyPoint
import kotlinx.coroutines.flow.Flow

interface ProxyRepository {

    fun getAvailableProxies(): List<ProxyPoint>

    fun add(point: ProxyPoint)

    fun remove(point: ProxyPoint)

    fun edit(point: ProxyPoint)
}
