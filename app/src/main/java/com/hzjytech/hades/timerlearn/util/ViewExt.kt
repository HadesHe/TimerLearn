package com.hzjytech.hades.timerlearn.util

import android.support.design.widget.Snackbar
import android.view.View
import java.time.Duration

/**
 * Created by Hades on 2018/2/1.
 */
fun View.showSnackBar(message:String,duration: Int){
    Snackbar.make(this,message,duration).show()
}