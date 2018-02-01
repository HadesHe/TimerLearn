package com.hzjytech.hades.timerlearn.tasks

import android.opengl.Visibility
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupMenu
import com.hzjytech.hades.timerlearn.R
import com.hzjytech.hades.timerlearn.data.Task
import com.hzjytech.hades.timerlearn.util.showSnackBar
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.android.synthetic.main.task_item.view.*

/**
 * Created by Hades on 2018/1/8.
 */
class TasksFragment: Fragment(),TasksContract.View {

    override fun onResume() {
        super.onResume()
        presenter.start()
    }
    override var isActive: Boolean= false
        get() =isAdded

    override lateinit var presenter: TasksContract.Presenter

    internal val itemListenr: TaskItemListener = object : TaskItemListener {
        override fun onCompleteTaskClick(task: Task) {
            presenter.completeTask(task)
        }

        override fun onActiviteTaskClick(task: Task) {
            presenter.activiteTask(task)
        }

        override fun onTaskClick(task: Task) {
            presenter.openTaskDetails(task)
        }
    }

    private val listAdapter = TasksAdapter(ArrayList(0), itemListenr)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_tasks, container, false)

        with(root) {

            lvTasksFrag.apply {
                adapter = listAdapter
            }

            scrollTasksFrag.apply {
                setColorSchemeColors(
                        ContextCompat.getColor(activity, R.color.colorPrimary),
                        ContextCompat.getColor(activity, R.color.colorAccent),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                )
                scrollUpChild = lvTasksFrag
                setOnRefreshListener {
                    presenter.loadTasks(false)
                }
            }

            tvTasksFragNoTasksAdd.also {
                it.setOnClickListener {
                    presenter.showAddTask()
                }
            }

        }

        activity.findViewById<FloatingActionButton>(R.id.fabTasksAdd)
                .apply {
                    setImageResource(R.drawable.ic_add)
                    setOnClickListener { presenter.addNewTask() }
                }
        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_clear -> presenter.clearCompletedTasks()
            R.id.menu_filter -> showFilteringPopUpMenu()
            R.id.menu_refresh -> presenter.loadTasks(true)
        }
        return true
    }

    private fun showFilteringPopUpMenu() {
        PopupMenu(context,activity.findViewById(R.id.menu_filter)).apply {
            menuInflater.inflate(R.menu.filter_tasks,menu)
            setOnMenuItemClickListener { item ->
                when(item.itemId){
                    R.id.active-> presenter.currentFiltering = TasksFilterType.ACTIVE_TASKS
                    R.id.completed-> presenter.currentFiltering = TasksFilterType.COMPLETE_TASKS
                    else -> presenter.currentFiltering=TasksFilterType.ALL_TASKS
                }
                presenter.loadTasks(false)
                true
            }
            show()
        }
    }


    private class TasksAdapter(tasks: List<Task>, private val itemListener: TaskItemListener) : BaseAdapter() {

        var tasks: List<Task> = tasks
            set(tasks) {
                field = tasks
                notifyDataSetChanged()
            }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val task = getItem(position)
            val rowView = convertView ?: LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_item, parent, false)
            with(rowView.title) {
                text = task.titleForList
            }

            with(rowView.complete) {
                isChecked = task.isCompleted

                setOnClickListener {
                    if (task.isCompleted.not()) {
                        itemListener.onCompleteTaskClick(task)
                    } else {
                        itemListener.onActiviteTaskClick(task)
                    }
                }
            }
            rowView.setOnClickListener {
                itemListener.onTaskClick(task)
            }
            return rowView


        }

        override fun getItem(position: Int) = tasks[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = tasks.size

    }

    companion object {
        fun newInstance() = TasksFragment()
    }


    interface TaskItemListener {
        fun onCompleteTaskClick(task: Task)
        fun onActiviteTaskClick(task: Task)
        fun onTaskClick(task: Task)

    }

    override fun setLoadingIndicator(b: Boolean) {
        val root=view?:return;

        with(root.findViewById<SwipeRefreshLayout>(R.id.scrollTasksFrag)){
            post {
                isRefreshing=b
            }

        }
    }

    override fun showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTasks(tasks: List<Task>) {
        listAdapter.tasks=tasks
        llTasksFrag.visibility=View.VISIBLE
        llTasksFragNoTasks.visibility=View.GONE
    }

    override fun showActiveFilterLable() {
        filteringLable.text=resources.getString(R.string.label_active)
    }

    override fun showCompletedFilterLable() {
        filteringLable.text=resources.getString(R.string.label_completed)
    }

    override fun showAllFilterLabel() {
        filteringLable.text=resources.getString(R.string.label_all)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoActiveTasks() {
        showNoTaksViews(resources.getString(R.string.no_tasks_active),R.drawable.ic_check_circle_24dp,false)
    }

    private fun showNoTaksViews(mainText: String, iconRes: Int, showAddView: Boolean) {
        llTasksFrag.visibility=View.GONE
        llTasksFragNoTasks.visibility=View.VISIBLE

        tvTasksFragNoTasksAll.text=mainText
        ivTasksFragNoTasks.setImageResource(iconRes)

        tvTasksFragNoTasksAdd.visibility=if(showAddView)View.VISIBLE else View.GONE


    }

    override fun showNoCompletedTasks() {
        showNoTaksViews(resources.getString(R.string.no_tasks_completed),R.drawable.ic_verified_user_24dp,false)
    }

    override fun showNoTasks() {
        showNoTaksViews(resources.getString(R.string.no_tasks_all),R.drawable.ic_assignment_turned_in_24dp,false)
    }

    override fun showAddTask() {
        TODO("AddEditTaskActivity::class.java")
    }

    override fun showTaskDetailsUi(id: String) {
        TODO("TaskDetailActivity::class.java")
    }

    override fun showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete))
    }

    override fun showTaskMarkedActive() {
        showMessage(getString(R.string.task_marked_active))
    }

    override fun showCompletedTasksCleared() {
        showMessage(getString(R.string.completed_tasks_cleared))
    }

    private fun showMessage(message: String) {
        view?.showSnackBar(message,Snackbar.LENGTH_LONG)

    }


}








