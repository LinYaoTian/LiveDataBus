package com.lyt.livedatabus

import androidx.lifecycle.BusLiveData

interface Events {
    fun event():BusLiveData<String>
}