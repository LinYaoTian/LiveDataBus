package com.lyt.livedatabus


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private const val mEventA = "event_A"
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //测试
        btn_post.setOnClickListener {
           LiveDataBus
               .get<String>(mEventA)
               .postValue(et_input.text.toString())
        }
        LiveDataBus
            .get<String>(mEventA)
            .observe(this, Observer {
            result_observe_A.text = it
        })

        //测试非粘性订阅和取消订阅
        val observerB = Observer<String> {
            result_observe_B.text = it
        }
        btn_subscribe_B.setOnClickListener {
            LiveDataBus
                .get<String>(mEventA)
                .observe(this, observerB)
        }
        btn_unsubscribe_B.setOnClickListener {
            LiveDataBus
                .get<String>(mEventA)
                .removeObserver(observerB)
        }

        //测试粘性订阅和取消订阅
        val observerC = Observer<String> {
            result_observe_C.text = it
        }
        btn_subscribe_C.setOnClickListener {
            LiveDataBus
                .get<String>(mEventA)
                .observeSticky(this, observerC)
        }
        btn_unsubscribe_C.setOnClickListener {
            LiveDataBus
                .get<String>(mEventA)
                .removeObserver(observerC)
        }

        // 测试不受Activity生命周期影响的订阅
        // 在 Activity 退出后通过 Log 可以看到还会一直收到消息
        Thread(Runnable {
            var i = 0
            while (true){
                Thread.sleep(1000)
                LiveDataBus
                    .of(Events::class.java)
                    .event()
                    .postValue(i.toString())
                i++
            }

        }).start()

        LiveDataBus
            .of(Events::class.java)
            .event()
            .observeStickyForever(Observer {
                Log.d(TAG, "observeStickyForever: it = $it")
        })
    }
}
