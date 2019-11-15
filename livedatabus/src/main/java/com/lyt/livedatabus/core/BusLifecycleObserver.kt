package com.lyt.livedatabus.core

import androidx.lifecycle.*
import androidx.lifecycle.BusLiveData

internal class BusLifecycleObserver<T>(private val observer: Observer<in T>, private val owner: LifecycleOwner, private val liveData: BusLiveData<T>)
    : BaseBusObserverWrapper<T>(observer,liveData), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        liveData.removeObserver(observer)
        owner.lifecycle.removeObserver(this)
    }
}