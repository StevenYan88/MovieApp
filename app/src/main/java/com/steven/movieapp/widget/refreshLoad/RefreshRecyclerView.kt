package com.steven.movieapp.widget.refreshLoad

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Description:
 * Data：2019/2/20
 * Actor:Steven
 */
open class RefreshRecyclerView : WrapRecyclerView {
    private var refreshViewCreator: RefreshViewCreator? = null
    private var refreshView: View? = null
    private var fingerDownY: Int = 0
    private var refreshViewHeight: Int = 0
    private val dragIndex: Float = 0.35f
    private var currentDrag: Boolean = false
    private var currentRefreshStatus: Int = REFRESH_STATUS_NORMAL
    private var onRefreshListener: OnRefreshListener? = null
    private var isRefreshing = true

    companion object {
        //默认状态
        private const val REFRESH_STATUS_NORMAL = 0x0011

        //下拉刷新状态
        private const val REFRESH_STATUS_PULL_DOWN_REFRESH = 0x0022

        //松开刷新状态
        private const val REFRESH_STATUS_LOOSEN_REFRESHING = 0x0033

        //正在刷新状态
        private const val REFRESH_STATUS_REFRESHING = 0x0044
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        super.setAdapter(adapter)
        addRefreshView()
    }

    fun addRefreshViewCreator(refreshCreator: RefreshViewCreator) {
        this.refreshViewCreator = refreshCreator
        refreshView = refreshViewCreator?.getRefreshView(context, this)
    }

    private fun addRefreshView() {
        val adapter = adapter
        if (adapter != null) {
            addHeaderView(refreshView)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN ->
                fingerDownY = ev.rawY.toInt()
            MotionEvent.ACTION_UP -> if (currentDrag) {
                restoreRefreshView()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun restoreRefreshView() {
        if (refreshView == null) return
        val currentTopMargin = (refreshView?.layoutParams as MarginLayoutParams).topMargin
        var finalTopMargin = -refreshViewHeight + 1
        if (currentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            finalTopMargin = 0
            currentRefreshStatus = REFRESH_STATUS_REFRESHING
            refreshViewCreator?.onRefreshing()
            onRefreshListener?.apply {
                onRefresh()
            }
        }
        val distance = currentTopMargin - finalTopMargin
        val animator = ObjectAnimator.ofFloat(currentTopMargin.toFloat(), finalTopMargin.toFloat())
            .setDuration(distance.toLong())
        animator.addUpdateListener { animation ->
            val currentTooMargin = animation.animatedValue as Float
            setRefreshViewMarginTop(currentTooMargin.toInt())
        }
        animator.start()
        currentDrag = false
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (refreshViewHeight <= 0) {
            refreshViewHeight = refreshView?.measuredHeight ?: 0
            if (refreshViewHeight > 0) {
                setRefreshViewMarginTop(-refreshViewHeight + 1)
            }
        }
    }

    /**
     * 设置刷新topMargin
     */
    private fun setRefreshViewMarginTop(marginTop: Int) {
        if (refreshView == null) return
        var topMargin = marginTop
        val params = refreshView?.layoutParams as MarginLayoutParams
        if (topMargin < -refreshViewHeight + 1) {
            topMargin = -refreshViewHeight + 1
        }
        params.topMargin = topMargin
        refreshView?.layoutParams = params
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollUp() || currentRefreshStatus == REFRESH_STATUS_REFRESHING) {
                    isRefreshing = false
                    return super.onTouchEvent(e)
                }
                if (currentDrag) scrollToPosition(0)
                val distanceY = ((e.rawY - fingerDownY) * dragIndex).toInt()
                if (distanceY > 0) {
                    val marginTop = distanceY - refreshViewHeight
                    setRefreshViewMarginTop(marginTop)
                    updateRefreshStatus(distanceY)
                    isRefreshing = true
                    currentDrag = true
                    return false
                }
            }
        }
        return super.onTouchEvent(e)
    }

    private fun updateRefreshStatus(distanceY: Int) {
        currentRefreshStatus = when {
            distanceY <= 0 -> REFRESH_STATUS_NORMAL
            distanceY <= refreshViewHeight -> REFRESH_STATUS_PULL_DOWN_REFRESH
            else -> REFRESH_STATUS_LOOSEN_REFRESHING
        }
        refreshViewCreator?.onPull(distanceY, refreshViewHeight, currentRefreshStatus)
    }

    private fun canScrollUp(): Boolean {
        return this.canScrollVertically(-1)
    }

    fun onStopRefresh() {
        currentRefreshStatus = REFRESH_STATUS_NORMAL
        restoreRefreshView()
        refreshViewCreator?.onStopRefresh()
    }

    fun setOnRefreshListener(listener: OnRefreshListener) {
        this.onRefreshListener = listener
    }

    fun isRefreshing(): Boolean = isRefreshing


    interface OnRefreshListener {
        fun onRefresh()
    }
}
