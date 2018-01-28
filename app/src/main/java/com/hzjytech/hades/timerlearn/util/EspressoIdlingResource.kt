package com.hzjytech.hades.timerlearn.util

/**
 * Created by zhanghehe on 2018/1/26.
 */
object EspressoIdlingResource{

    private val RESOURCE="GLOBAL"

    val coutingIdlingResource=SimpleCoutingIdlingResource(RESOURCE)

    fun increment(){
        coutingIdlingResource.increment()
    }

    fun decrement(){
        coutingIdlingResource.decrement()
    }

}