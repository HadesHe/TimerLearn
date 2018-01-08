package com.hzjytech.hades.timerlearn.ui

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

/**
 * Created by Hades on 2018/1/8.
 */
class ScrollChildSwipeRefreshLayout constructor(context:Context,attrs:AttributeSet?=null): SwipeRefreshLayout(context, attrs) {

    var scrollUpChild: View?=null

    override fun canChildScrollUp(): Boolean {
        return scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()
    }

}