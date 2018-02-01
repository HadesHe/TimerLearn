package com.hzjytech.hades.timerlearn

object Injection{
    fun provideTasksRepository(context: Context): TasksRepository {
        val database=ToDoDataBase.getInstance(context)
        return TasksRepository.getInstance(FakeTasks,
                TasksLocalDataSource.getInstance(AppExecutors(), database.taskDao()))
    }
}