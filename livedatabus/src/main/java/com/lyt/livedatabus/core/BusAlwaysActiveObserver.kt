package com.lyt.livedatabus.core

import androidx.lifecycle.BusLiveData
import androidx.lifecycle.Observer

internal class BusAlwaysActiveObserver<T>(private val mObserver: Observer<in T>, private val mBusLiveData: BusLiveData<T>)
    : BaseBusObserverWrapper<T>(mObserver, mBusLiveData)