package com.hzjytech.hades.timerlearn.tasks

import com.hzjytech.hades.timerlearn.BasePresenter
import com.hzjytech.hades.timerlearn.BaseView
import com.hzjytech.hades.timerlearn.data.Task

/**
 * Created by Hades on 2018/1/8.
 */
interface TasksContract{

    interface View:BaseView<Presenter>{
        val isActive: Boolean
        fun setLoadingIndicator(b: Boolean)
        fun showLoadingTasksError()
        fun showTasks(tasks: List<Task>)
        fun showActiveFilterLable()
        fun showCompletedFilterLable()
        fun showAllFilterLabel()
        fun showNoActiveTasks()
        fun showNoCompletedTasks()
        fun showNoTasks()
        fun showAddTask()
        fun showTaskDetailsUi(id: String)
        fun showTaskMarkedComplete()
        fun showTaskMarkedActive()
        fun showCompletedTasksCleared()

    }

    interface Presenter:BasePresenter{
        fun completeTask(task: Task)
        fun activiteTask(task: Task)
        fun openTaskDetails(task: Task)
        fun loadTasks(b: Boolean)
        fun showAddTask()
        fun addNewTask()
        fun clearCompletedTasks()
        var currentFiltering: TasksFilterType

    }
}