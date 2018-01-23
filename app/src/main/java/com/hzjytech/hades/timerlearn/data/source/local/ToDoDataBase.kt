package com.hzjytech.hades.timerlearn.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.hzjytech.hades.timerlearn.data.Task

/**
 * Created by zhanghehe on 2018/1/21.
 */
@Database(entities = arrayOf(Task::class),version = 1)
abstract class ToDoDataBase: RoomDatabase(){

    abstract fun taskDao():TasksDao

    companion object {
        private var INSTANCE:ToDoDataBase?=null

        private var lock=Any()

        fun getInstance(context :Context):ToDoDataBase {
            synchronized(lock) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ToDoDataBase::class.java, "Tasks.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }

}