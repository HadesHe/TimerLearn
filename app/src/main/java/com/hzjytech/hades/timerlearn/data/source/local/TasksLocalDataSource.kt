package com.hzjytech.hades.timerlearn.data.source.local

import android.support.annotation.VisibleForTesting
import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksDataSource
import com.hzjytech.hades.timerlearn.util.AppExecutors

/**
 * Created by zhanghehe on 2018/1/21.
 */
class TasksLocalDataSource private constructor(
        val appExecutor: AppExecutors,
        val tasksDao: TasksDao
        ):TasksDataSource{
    override fun activateTask(taskId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        appExecutor.diskIO.execute {
            val tasks=tasksDao.getTasks()
            appExecutor.mainThread.execute {
                if(tasks.isEmpty()){
                    callback.onDataNotAvailable()
                }else{
                    callback.onTasksLoaded(tasks)
                }
            }
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        appExecutor.diskIO.execute {
            val task=tasksDao.getTaskById(taskId)
            appExecutor.mainThread.execute {
                if (task != null){
                    callback.onTaskLoaded(task)
                }else {
                    callback.onDataNotAvailable()
                }
            }

        }
    }

    override fun saveTask(task: Task) {
        appExecutor.diskIO.execute {
            tasksDao.insertTask(task)
        }
    }

    override fun completeTask(task: Task) {
        appExecutor.diskIO.execute {
            tasksDao.updateCompleted(task.id,true)
        }
    }

    override fun completeTask(taskId: String) {
        appExecutor.diskIO.execute {
            tasksDao.updateCompleted(taskId ,true)
        }
    }

    override fun activateTask(task: Task) {
    }

    override fun clearCompletedTasks() {
        appExecutor.diskIO.execute {
            tasksDao.deleteCompletedTasks()
        }
    }

    override fun deleteAllTasks() {
        appExecutor.diskIO.execute {
            tasksDao.deleteTasks()
        }
    }

    override fun deleteTask(taskId: String) {
        appExecutor.diskIO.execute {
            tasksDao.deleteTaskById(taskId)
        }
    }

    companion object {
        private var INSTANCE:TasksLocalDataSource?=null

        fun getInstance(appExecutor: AppExecutors,tasksDao: TasksDao):TasksLocalDataSource{
            if(INSTANCE==null){
                synchronized(TasksDataSource::javaClass){
                    INSTANCE=TasksLocalDataSource(appExecutor,tasksDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance(){
            INSTANCE=null
        }
    }

}