package com.hzjytech.hades.timerlearn.data.source

import com.hzjytech.hades.timerlearn.data.Task

/**
 * Created by zhanghehe on 2018/1/16.
 */
interface TasksDataSource{

    interface LoadTasksCallback{
        fun onTasksLoaded(tasks:List<Task>)

        fun onDataNotAvailable()
    }

    interface GetTaskCallback{
        fun onTaskLoaded(task:Task)

        fun onDataNotAvailable()
    }

    fun getTasks(callback: LoadTasksCallback)

    fun getTask(taskId:String,callback: GetTaskCallback)

    fun saveTask(taskId:String,callback: GetTaskCallback)

    fun completeTask(task:Task)

    fun completeTask(taskId:String)

    fun activateTask(task:Task)

    fun clearCompletedTasks()

    fun deleteAllTasks()

    fun deleteTask(taskId:String)
}