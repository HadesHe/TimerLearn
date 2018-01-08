package com.hzjytech.hades.timerlearn.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.hzjytech.hades.timerlearn.R

/**
 * Created by Hades on 2018/1/8.
 */
class TasksFragment: Fragment(),TasksContract.View{

    override lateinit var presenter: TasksContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root=inflater.inflate(R.layout.fragment_tasks,container,false)

        with(root){
            val listView =findViewById<ListView>(R.id.lvTasksFrag).apply {
              adapter=listAdapter
            }
        }




    }


}