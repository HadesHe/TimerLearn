package com.hzjytech.hades.timerlearn.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by zhanghehe on 2018/1/24.
 */
class DiskIOThreadExecutor:Executor{

    private val diskIO= Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) {
        diskIO.execute(command)
    }

}