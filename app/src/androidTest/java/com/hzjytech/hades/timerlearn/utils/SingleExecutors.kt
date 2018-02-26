package com.hzjytech.hades.timerlearn.utils

import com.hzjytech.hades.timerlearn.util.AppExecutors
import java.util.concurrent.Executor

/**
 * Created by zhanghehe on 2018/2/11.
 */
class SingleExecutors:AppExecutors(instant,instant,instant){
    companion object {
        private val instant=Executor{command -> command.run() }
    }
}