package com.hzjytech.hades.timerlearn.tasks

import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.data.source.TasksDataSource
import com.hzjytech.hades.timerlearn.data.source.TasksRepository
import com.hzjytech.hades.timerlearn.util.EspressoIdlingResource

/**
 * Created by zhanghehe on 2018/1/13.
 */
class TasksPresenter(val tasksRepository: TasksRepository, val tasksView:TasksContract.View ):TasksContract.Presenter{

    private var firstLoad=true


    init {
        tasksView.presenter=this
    }

    override fun start() {
        loadTasks(false)
    }

    override fun completeTask(task: Task) {
        tasksRepository.completeTask(task)
        tasksView.showTaskMarkedComplete()
        loadTasks(false,false)
    }

    override fun activiteTask(task: Task) {
        tasksRepository.activateTask(task)
        tasksView.showTaskMarkedActive()
        loadTasks(false,false)
    }

    override fun openTaskDetails(task: Task) {
        tasksView.showTaskDetailsUi(task.id)
    }

    override fun loadTasks(forceUpdate: Boolean) {
        loadTasks(forceUpdate||firstLoad,true)
        firstLoad=false
    }

    private fun loadTasks(forceUpdate: Boolean,showLoadingUI:Boolean){
        if(showLoadingUI){
            tasksView.setLoadingIndicator(true)
        }
        if(forceUpdate){
            tasksRepository.refreshTasks()
        }

        EspressoIdlingResource.increment()

        tasksRepository.getTasks(object:TasksDataSource.LoadTasksCallback{
            override fun onTasksLoaded(tasks: List<Task>) {
                val tasksToShow=ArrayList<Task>()
                if(!EspressoIdlingResource.coutingIdlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }

                for (task in tasks) {
                    when(currentFiltering){
                        TasksFilterType.ACTIVE_TASKS-> tasksToShow.add(task)
                        TasksFilterType.ACTIVE_TASKS-> if(task.isActive){
                            tasksToShow.add(task)
                        }
                        TasksFilterType.COMPLETE_TASKS -> if(task.isCompleted){
                            tasksToShow.add(task)
                        }
                    }
                }

                if(!tasksView.isActive){
                    return
                }

                if(showLoadingUI){
                    tasksView.setLoadingIndicator(false)
                }

                processTasks(tasksToShow)

            }

            override fun onDataNotAvailable() {
                if(!tasksView.isActive){
                    return
                }

                tasksView.showLoadingTasksError()
            }
        })

    }

    private fun processTasks(tasks:List<Task>){
        if (tasks.isEmpty()){
            processEmptyTasks()
        }else{
            tasksView.showTasks(tasks)
            showFilterLabel()
        }
    }

    private fun showFilterLabel() {
        when (currentFiltering) {
            TasksFilterType.ACTIVE_TASKS -> {
                tasksView.showActiveFilterLable()
            }

        }
    }

    private fun processEmptyTasks() {
        when(currentFiltering){
            TasksFilterType.ACTIVE_TASKS->tasksView.showNoActiveTasks()
            TasksFilterType.COMPLETE_TASKS->tasksView.showNoCompletedTasks()
            else -> tasksView.showNoTasks()
        }


    }

    override fun showAddTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addNewTask() {
        tasksView.showAddTask()
    }

    override fun clearCompletedTasks() {
        tasksRepository.clearCompletedTasks()
        tasksView.showCompletedTasksCleared()
        loadTasks(false,false)
    }


    override var currentFiltering: TasksFilterType = TasksFilterType.ALL_TASKS

}