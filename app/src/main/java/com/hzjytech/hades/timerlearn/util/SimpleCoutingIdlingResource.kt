package com.hzjytech.hades.timerlearn.util

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by zhanghehe on 2018/1/26.
 */
class SimpleCoutingIdlingResource(val resourceName:String):IdlingResource{

    private val counter=AtomicInteger(0)

    @Volatile
    private var resourceCallback:IdlingResource.ResourceCallback?=null

    override fun getName()=resourceName

    override fun isIdleNow()=counter.get()==0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.resourceCallback=resourceCallback
    }

    fun increment(){
        counter.getAndIncrement()
    }

    fun decrement(){
        val counterVal=counter.decrementAndGet()
        if(counterVal==0){
            resourceCallback?.onTransitionToIdle()
        }

        if(counterVal<0){
            throw IllegalArgumentException("Counter has been corrupted!")
        }

    }

}