package com.hzjytech.hades.timerlearn.data.source.remote

import android.os.Handler
import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksDataSource

/**
 * Created by zhanghehe on 2018/1/17.
 */
object TasksRemoteDataSource:TasksDataSource{

    private var TASKS_SERVICE_DATA=LinkedHashMap<String,Task>(2)

    init {
        addTask("Build tower in Pisa","Ground looks good, no founation work required")
        addTask("Finish bridge in Tacoma","Found awesine girders at half the cost")

    }

    private fun addTask(title:String,description:String){
        val newTask=Task(title,description)
        TASKS_SERVICE_DATA.put(newTask.id,newTask)
    }

    private val SERVICE_LATENCY_IN_MILLIS: Long = 5000L

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {

        val tasks=TASKS_SERVICE_DATA.values.toList()
        Handler().postDelayed({
            callback.onTasksLoaded(tasks)
        },SERVICE_LATENCY_IN_MILLIS)
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val task= TASKS_SERVICE_DATA[taskId]
        with(Handler()){
            if(task != null){
                postDelayed({callback.onTaskLoaded(task)}, SERVICE_LATENCY_IN_MILLIS)
            }else{
                postDelayed({callback.onDataNotAvailable()}, SERVICE_LATENCY_IN_MILLIS)
            }
        }
    }

    override fun saveTask(taskId:Task) {
        TASKS_SERVICE_DATA.put(taskId.id,taskId)
    }

    override fun completeTask(task: Task) {
        val completedTask=Task(task.title,task.description,task.id).apply {
            isCompleted=true
        }
        TASKS_SERVICE_DATA.put(task.id,completedTask)
    }

    override fun completeTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun activateTask(task: Task) {
        val activeTaks=Task(task.title,task.description,task.id)
        TASKS_SERVICE_DATA.put(task.id,activeTaks)
    }

    override fun clearCompletedTasks() {
        TASKS_SERVICE_DATA= TASKS_SERVICE_DATA.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
    }

    override fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }

}