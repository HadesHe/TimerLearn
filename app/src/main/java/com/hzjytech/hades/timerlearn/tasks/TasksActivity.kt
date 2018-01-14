package com.hzjytech.hades.timerlearn.tasks

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hzjytech.hades.timerlearn.R
import com.hzjytech.hades.timerlearn.util.replaceFragmentInActivity
import com.hzjytech.hades.timerlearn.util.setupActionBar
import kotlinx.android.synthetic.main.activity_tasks.*

/**
 * Created by Hades on 2018/1/5.
 */
class TasksActivity : AppCompatActivity() {

    private lateinit var tasksPresenter: TasksPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        setupActionBar(R.id.toolbTasks) {
            setHomeAsUpIndicator(R.drawable.ic_menu);
            setDisplayHomeAsUpEnabled(true)

        }

        drawerTasks.apply {
            setStatusBarBackground(R.color.colorPrimaryDark)

        }
        setupDrawerContent(navTasks)
        val tasksFragment = supportFragmentManager.findFragmentById(R.id.frameTasks)
                as TasksFragment? ?: TasksFragment.newInstance().also {
            replaceFragmentInActivity(it,R.id.frameTasks)

        }

        tasksPresenter=TasksPresenter(tasksFragment).apply {
            if (savedInstanceState != null) {
                currentFiltering=savedInstanceState.getSerializable(CURRENT_FILTERING_KEY) as TasksFilterType
            }
        }


    }

    private val CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putSerializable(CURRENT_FILTERING_KEY,tasksPresenter.currentFiltering)
        })
    }

    private fun setupDrawerContent(navTasks: NavigationView) {
        navTasks.setNavigationItemSelectedListener {
            if (it.itemId == R.id.statistics_navigation_menu_item) {
                //start StatisticsActivity
            }

            it.isChecked = true
            drawerTasks.closeDrawers()
            true
        }
    }


}