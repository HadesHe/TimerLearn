package com.hzjytech.hades.timerlearn.tasks

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import com.hzjytech.hades.timerlearn.R
import com.hzjytech.hades.timerlearn.util.setupActionBar

/**
 * Created by Hades on 2018/1/5.
 */
class TasksActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        setupActionBar(R.id.toolbTasks){
            setHomeAsUpIndicator(R.drawable.ic_menu);
            setDisplayHomeAsUpEnabled(true)

        }

        drawerTasks=findViewById<DrawerLayout>(R.id.drawerTasks)


    }



}