package com.hzjytech.hades.timerlearn.data.source.local

import android.arch.persistence.room.*
import com.hzjytech.hades.timerlearn.data.Task

/**
 * Created by zhanghehe on 2018/1/23.
 */
@Dao interface TasksDao{

    @Query("SELECT * FROM Tasks") fun getTasks():List<Task>


    @Query("select * from Tasks where entryid=:taskId")fun getTaskById(taskId: String):Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertTask(task:Task)

    @Update fun updateTask(task: Task):Int

    @Query("update tasks set completed=: completed where entryid=:taskId")
    fun updateCompleted(taskId:String,completed:Boolean)

    @Query("delete from tasks where entryid=taskId")
    fun deleteTaskById(taskId: String):Int

    @Query("delete from tasks")
    fun deleteTasks()

    @Query("delete from tasks where completed=1")
    fun deleteCompletedTasks():Int
}