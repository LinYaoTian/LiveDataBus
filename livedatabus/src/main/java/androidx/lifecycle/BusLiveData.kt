package androidx.lifecycle

import androidx.annotation.MainThread
import com.lyt.livedatabus.Logger
import com.lyt.livedatabus.core.BaseBusObserverWrapper
import com.lyt.livedatabus.core.BusAlwaysActiveObserver
import com.lyt.livedatabus.core.BusLifecycleObserver
import com.lyt.livedatabus.core.LiveDataBusCore


class BusLiveData<T>(private val mKey:String) : MutableLiveData<T>() {

    private val TAG = "BusLiveData"

    private val mObserverMap: MutableMap<Observer<in T>, BaseBusObserverWrapper<T>> = mutableMapOf()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val exist = mObserverMap.getOrPut(observer,{
            BusLifecycleObserver(observer,owner,this).apply {
                mObserverMap[observer] = this
                owner.lifecycle.addObserver(this)
            }
        })
        super.observe(owner, exist)
        Logger.d(TAG,"observe() called with: owner = [$owner], observer = [$observer]")
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(observer)
        val exist = mObserverMap.getOrPut(observer ,{
            BusAlwaysActiveObserver(observer,this).apply {
                mObserverMap[observer] = this
            }
        })
        super.observeForever(exist)
        Logger.d(TAG, "observeForever() called with: observer = [$observer]")
    }

    @MainThread
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, observer)
        Logger.d(TAG, "observeSticky() called with: owner = [$owner], observer = [$observer]")
    }

    @MainThread
    fun observeStickyForever(observer: Observer<T>){
        super.observeForever(observer)
        Logger.d(TAG, "observeStickyForever() called with: observer = [$observer]")
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        val exist = mObserverMap.remove(observer) ?: observer
        super.removeObserver(exist)
        Logger.d(TAG, "removeObserver() called with: observer = [$observer]")
    }

    @MainThread
    override fun removeObservers(owner: LifecycleOwner) {
        mObserverMap.iterator().forEach {
            if (it.value.isAttachedTo(owner)) {
                mObserverMap.remove(it.key)
            }
        }
        super.removeObservers(owner)
        Logger.d(TAG, "removeObservers() called with: owner = [$owner]")
    }

    @MainThread
    override fun onInactive() {
        super.onInactive()
        if (!hasObservers()) {
            // 当 LiveData 没有活跃的观察者时，可以移除相关的实例
            LiveDataBusCore.getInstance().mBusMap.remove(mKey)
        }
        Logger.d(TAG, "onInactive() called")
    }

    @MainThread
    public override fun getVersion(): Int {
        return super.getVersion()
    }


}