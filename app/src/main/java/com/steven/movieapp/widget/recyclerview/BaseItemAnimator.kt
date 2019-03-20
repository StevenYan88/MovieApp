package com.steven.movieapp.widget.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

/**
 * Description:
 * Data：3/14/2019-11:33 AM
 * @author yanzhiwen
 */
class BaseItemAnimator:SimpleItemAnimator(){
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runPendingAnimations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRunning(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endAnimations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}