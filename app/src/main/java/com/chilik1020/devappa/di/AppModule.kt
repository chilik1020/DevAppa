package com.chilik1020.devappa.di

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.chilik1020.devappa.ProtoProxyPoint
import com.chilik1020.devappa.ProxyViewModel
import com.chilik1020.devappa.data.proxyPointsDataStore
import com.chilik1020.devappa.repository.ProtoDataStoreRepository
import com.chilik1020.devappa.repository.ProtoProxyRepository
import com.chilik1020.devappa.repository.ProxyRepository
import com.chilik1020.devappa.repository.ProxyRepositoryLocal
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ProxyRepository> { ProxyRepositoryLocal() }
    viewModel { ProxyViewModel(get()) }
}