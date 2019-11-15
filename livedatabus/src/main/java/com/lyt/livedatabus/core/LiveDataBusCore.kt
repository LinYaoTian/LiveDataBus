package com.lyt.livedatabus.core

import androidx.lifecycle.BusLiveData

internal class LiveDataBusCore {

    companion object{

        @JvmStatic
        private val defaultBus = LiveDataBusCore()

        @JvmStatic
        fun getInstance() = defaultBus
    }

    internal val mBusMap : MutableMap<String, BusLiveData<*>> by lazy {
        mutableMapOf<String, BusLiveData<*>>()
    }

    fun <T> getChannel(key: String) : BusLiveData<T> {
        return mBusMap.getOrPut(key){
            BusLiveData<T>(key)
        } as BusLiveData<T>
    }
}