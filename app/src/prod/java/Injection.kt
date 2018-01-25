/**
 * Created by zhanghehe on 2018/1/25.
 */
package com.hzjytech.hades.timerlearn

import android.content.Context
import com.hzjytech.hades.timerlearn.data.source.TasksRepository
import com.hzjytech.hades.timerlearn.data.source.local.TasksLocalDataSource
import com.hzjytech.hades.timerlearn.data.source.local.ToDoDataBase
import com.hzjytech.hades.timerlearn.data.source.remote.TasksRemoteDataSource
import com.hzjytech.hades.timerlearn.util.AppExecutors

object Injection{
    fun provideTasksRepository(context: Context): TasksRepository {
        val database=ToDoDataBase.getInstance(context)
        return TasksRepository.getInstance(TasksRemoteDataSource,
                TasksLocalDataSource.getInstance(AppExecutors(), database.taskDao()))
    }

}