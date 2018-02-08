package com.hzjytech.hades.timerlearn.data.source.local

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Created by zhanghehe on 2018/2/8.
 */
@RunWith(AndroidJUnit4::class)
class TasksDaoTest{


    private lateinit var database: ToDoDataBase

    @Before fun initDb(){
        database =Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ToDoDataBase::class.java).build()
    }

    @After fun closeDb()=database.close()


}