package com.hzjytech.hades.timerlearn.tasks

import com.hzjytech.hades.timerlearn.BasePresenter
import com.hzjytech.hades.timerlearn.BaseView
import com.hzjytech.hades.timerlearn.data.Task

/**
 * Created by Hades on 2018/1/8.
 */
interface TasksContract{

    interface View:BaseView<Presenter>{

    }

    interface Presenter:BasePresenter{
        fun completeTask(task: Task)
        fun activiteTask(task: Task)
        fun openTaskDetails(task: Task)
        fun loadTasks(b: Boolean)
        fun showAddTask()
        fun addNewTask()
        fun clearCompletedTasks()
        fun showFilteringPopUpMenu()

    }
}