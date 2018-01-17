package com.hzjytech.hades.timerlearn.data.source.remote

import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksDataSource

/**
 * Created by zhanghehe on 2018/1/17.
 */
object TasksRemoteDataSource:TasksDataSource{

    private var TASKS_SERVICE_DATA=LinkedHashMap<String,Task>(2)

    init {
        addTask("Build tower in Pisa","Ground looks good, no founation work required")
        addTask("Build tower in Pisa","Ground looks good, no founation work required")

    }

    private fun addTask(title:String,description:String){
        val newTask=Task(title,description)
        TASKS_SERVICE_DATA.put(newTask.id,newTask)
    }
    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearCompletedTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}