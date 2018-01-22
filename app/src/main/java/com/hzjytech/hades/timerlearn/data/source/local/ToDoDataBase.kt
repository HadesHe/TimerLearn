package com.hzjytech.hades.timerlearn.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hzjytech.hades.timerlearn.data.Task

/**
 * Created by zhanghehe on 2018/1/21.
 */
@Database(entities = arrayOf(Task::class),version = 1)
abstract class ToDoDataBase: RoomDatabase(){

}