package com.madreain.libhulk.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { action(it) })
}

fun <T, R> LifecycleOwner.observe(
    liveData1: LiveData<T>,
    liveData2: LiveData<R>,
    action: (t: T?, r: R?) -> Unit
) {
    liveData1.observe(this, Observer {
        action(liveData1.value, liveData2.value)
    })

    liveData2.observe(this, Observer {
        action(liveData1.value, liveData2.value)
    })
}

fun <T, R, V> LifecycleOwner.observe(
    liveData1: LiveData<T>,
    liveData2: LiveData<R>,
    liveData3: LiveData<V>,
    action: (t: T?, r: R?, v: V?) -> Unit
) {
    liveData1.observe(this, Observer {
        action(liveData1.value, liveData2.value, liveData3.value)
    })

    liveData2.observe(this, Observer {
        action(liveData1.value, liveData2.value, liveData3.value)
    })

    liveData3.observe(this, Observer {
        action(liveData1.value, liveData2.value, liveData3.value)
    })
}