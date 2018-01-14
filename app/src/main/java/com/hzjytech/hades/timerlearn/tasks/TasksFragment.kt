package com.hzjytech.hades.timerlearn.tasks

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import com.hzjytech.hades.timerlearn.R
import com.hzjytech.hades.timerlearn.data.Task
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.android.synthetic.main.task_item.view.*

/**
 * Created by Hades on 2018/1/8.
 */
class TasksFragment: Fragment(),TasksContract.View {

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
            val listView = findViewById<ListView>(R.id.lvTasksFrag).apply {
                adapter = listAdapter
            }

            scrollTasksFrag.apply {
                setColorSchemeColors(
                        ContextCompat.getColor(activity, R.color.colorPrimary),
                        ContextCompat.getColor(activity, R.color.colorAccent),
                        ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                )
                scrollUpChild = listView
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
            R.id.menu_filter -> presenter.showFilteringPopUpMenu()
            R.id.menu_refresh -> presenter.loadTasks(true)
        }
        return true
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
}








