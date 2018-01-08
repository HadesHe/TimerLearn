package com.hzjytech.hades.timerlearn.util

import android.support.annotation.IdRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

/**
 * Created by Hades on 2018/1/5.
 */
fun AppCompatActivity.setupActionBar(@IdRes toolbar:Int ,action: ActionBar.() -> Unit){
    setSupportActionBar(findViewById(toolbar))
    supportActionBar?.run {
        action
    }
}