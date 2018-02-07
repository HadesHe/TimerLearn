package com.hzjytech.hades.timerlearn.data.source

import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.local.TasksLocalDataSource
import com.hzjytech.hades.timerlearn.data.source.remote.TasksRemoteDataSource
import java.util.*

/**
 * Created by zhanghehe on 2018/1/16.
 */
public class TasksRepository(
        val taskRemoteDataSource: TasksDataSource,
        val tasksLocalDataSource: TasksDataSource
):TasksDataSource{
    override fun activateTask(taskId: String) {
        getTaskWithId(taskId)?.let {
            activateTask(it)
        }
    }

    override fun refreshTasks() {
        cacheIsDirty=true
    }

    var cachedTasks: LinkedHashMap<String, Task> = LinkedHashMap()

    var cacheIsDirty=false

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {

        if (cachedTasks.isNotEmpty()&&cacheIsDirty.not()){
            callback.onTasksLoaded(ArrayList(cachedTasks.values))
            return
        }

        if(cacheIsDirty){
            getTasksFromRemoteDataSource(callback)
        }else{
            tasksLocalDataSource.getTasks(object :TasksDataSource.LoadTasksCallback{
                override fun onTasksLoaded(tasks: List<Task>) {
                    refreshCache(tasks)
                    callback.onTasksLoaded(ArrayList(cachedTasks.values))
                }

                override fun onDataNotAvailable() {
                    getTasksFromRemoteDataSource(callback)
                }
            })
        }
    }

    private fun getTasksFromRemoteDataSource(callback: TasksDataSource.LoadTasksCallback){
        taskRemoteDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                refreshCache(tasks)
                refreshLocalDataSource(tasks)
                callback.onTasksLoaded(ArrayList(cachedTasks.values))
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshCache(tasks:List<Task>){
        cachedTasks.clear()
        tasks.forEach {
            cacheAndPerform(it){}
        }

        cacheIsDirty=false
    }

    private fun refreshLocalDataSource(tasks:List<Task>){
        tasksLocalDataSource.deleteAllTasks()
        for (task in tasks) {
            tasksLocalDataSource.saveTask(task)
        }
    }

    private fun getTaskWithId(id:String)=cachedTasks[id]

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val taskInCache=getTaskWithId(taskId)

        if(taskInCache != null){
            callback.onTaskLoaded(taskInCache)
            return
        }

        tasksLocalDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {

            override fun onTaskLoaded(task: Task) {
                cacheAndPerform(task){
                    callback.onTaskLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTask(task: Task) {
        cacheAndPerform(task){
            taskRemoteDataSource.saveTask(it)
            tasksLocalDataSource.saveTask(it)
        }
    }

    override fun completeTask(task: Task) {
       cacheAndPerform(task){
           it.isCompleted=true
           taskRemoteDataSource.completeTask(it)
           tasksLocalDataSource.completeTask(it)
       }
    }

    override fun completeTask(taskId: String) {
        getTaskWithId(taskId)?.let {
            completeTask(it)
        }
    }

    override fun activateTask(task: Task) {
        cacheAndPerform(task){
            it.isCompleted=false
            taskRemoteDataSource.activateTask(it)
            tasksLocalDataSource.activateTask(it)
        }
    }

    private inline fun cacheAndPerform(task:Task,perform:(Task)->Unit){
        val cachedTask=Task(task.id,task.description,task.id).apply {
            isCompleted=task.isCompleted
        }
        cachedTasks.put(cachedTask.id,cachedTask)
        perform(cachedTask)
    }

    override fun clearCompletedTasks() {
        tasksLocalDataSource.clearCompletedTasks()
        taskRemoteDataSource.clearCompletedTasks()

        cachedTasks = cachedTasks.filterValues {
            it.isCompleted.not()
        } as LinkedHashMap<String, Task>
    }

    override fun deleteAllTasks() {
        tasksLocalDataSource.deleteAllTasks()
        taskRemoteDataSource.deleteAllTasks()
        cachedTasks.clear()
    }

    override fun deleteTask(taskId: String) {
        taskRemoteDataSource.deleteTask(taskId)
        tasksLocalDataSource.deleteTask(taskId)
        cachedTasks.remove(taskId)
    }

    companion object {
        private var INSTANCE: TasksRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        @JvmStatic fun getInstance(tasksRemoteDataSource: TasksDataSource,
                                   tasksLocalDataSource: TasksDataSource): TasksRepository {
            return INSTANCE ?: TasksRepository(tasksRemoteDataSource, tasksLocalDataSource)
                    .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }

}