package com.chilik1020.devappa.di

import androidx.datastore.core.DataStore
import com.chilik1020.devappa.Proxies
import com.chilik1020.devappa.ProxyViewModel
import com.chilik1020.devappa.data.proxyPointsDataStore
import com.chilik1020.devappa.data.ProtoDataStoreRepository
import com.chilik1020.devappa.data.ProtoProxyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<DataStore<Proxies>> { androidContext().proxyPointsDataStore}
    single<ProtoProxyRepository> { ProtoDataStoreRepository(get()) }
    viewModel { ProxyViewModel(get()) }
}