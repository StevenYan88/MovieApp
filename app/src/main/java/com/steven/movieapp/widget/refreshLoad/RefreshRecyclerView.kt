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
    private lateinit var mRefreshCreator: RefreshViewCreator
    private lateinit var mRefreshView: View
    private var mFingerDownY: Int = 0
    private var mRefreshViewHeight: Int = 0
    private val mDragIndex: Float = 0.35f
    private var mCurrentDrag: Boolean = false
    private var mCurrentRefreshStatus: Int =
            REFRESH_STATUS_NORMAL
    private var mListener: OnRefreshListener? = null
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
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        super.setAdapter(adapter)
        addRefreshView()
    }

    fun addRefreshViewCreator(refreshCreator: RefreshViewCreator) {
        this.mRefreshCreator = refreshCreator
        mRefreshView = mRefreshCreator.getRefreshView(context, this)
    }

    private fun addRefreshView() {
        val adapter = adapter
        if (adapter != null) {
            addHeaderView(mRefreshView)
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN ->
                mFingerDownY = ev.rawY.toInt()
            MotionEvent.ACTION_UP -> if (mCurrentDrag) {
                restoreRefreshView()
            }


        }
        return super.dispatchTouchEvent(ev)
    }

    private fun restoreRefreshView() {
        val currentTopMargin = (mRefreshView.layoutParams as MarginLayoutParams).topMargin
        var finalTopMargin = -mRefreshViewHeight + 1
        if (mCurrentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            finalTopMargin = 0
            mCurrentRefreshStatus =
                    REFRESH_STATUS_REFRESHING
            mRefreshCreator.onRefreshing()

            mListener?.apply {
                onRefresh()
            }

        }
        val distance = currentTopMargin - finalTopMargin

        val animator = ObjectAnimator.ofFloat(currentTopMargin.toFloat(), finalTopMargin.toFloat())
                .setDuration(distance.toLong())
        animator.addUpdateListener { animation ->
            @Suppress("NAME_SHADOWING")
            val currentTooMargin = animation.animatedValue as Float
            setRefreshViewMarginTop(currentTooMargin.toInt())
        }
        animator.start()
        mCurrentDrag = false
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mRefreshViewHeight <= 0) {
            mRefreshViewHeight = mRefreshView.measuredHeight
            if (mRefreshViewHeight > 0) {
                setRefreshViewMarginTop(-mRefreshViewHeight + 1)
            }
        }
    }


    /**
     * 设置刷新topMargin
     */
    private fun setRefreshViewMarginTop(marginTop: Int) {
        var topMargin = marginTop
        val params = mRefreshView.layoutParams as MarginLayoutParams
        if (topMargin < -mRefreshViewHeight + 1) {
            topMargin = -mRefreshViewHeight + 1
        }
        params.topMargin = topMargin
        mRefreshView.layoutParams = params
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollUp() || mCurrentRefreshStatus == REFRESH_STATUS_REFRESHING) {
                    isRefreshing = false
                    return super.onTouchEvent(e)
                }
                if (mCurrentDrag) scrollToPosition(0)

                val distanceY = ((e.rawY - mFingerDownY) * mDragIndex).toInt()
                if (distanceY > 0) {
                    val marginTop = distanceY - mRefreshViewHeight
                    setRefreshViewMarginTop(marginTop)
                    updateRefreshStatus(distanceY)
                    isRefreshing = true
                    mCurrentDrag = true
                    return false
                }
            }
        }
        return super.onTouchEvent(e)
    }

    private fun updateRefreshStatus(distanceY: Int) {
        mCurrentRefreshStatus = when {
            distanceY <= 0 -> REFRESH_STATUS_NORMAL
            distanceY <= mRefreshViewHeight -> REFRESH_STATUS_PULL_DOWN_REFRESH
            else -> REFRESH_STATUS_LOOSEN_REFRESHING
        }
        mRefreshCreator.onPull(distanceY, mRefreshViewHeight, mCurrentRefreshStatus)
    }

    private fun canScrollUp(): Boolean {
        return this.canScrollVertically(-1)
    }

    fun onStopRefresh() {
        mCurrentRefreshStatus =
                REFRESH_STATUS_NORMAL
        restoreRefreshView()
        mRefreshCreator.onStopRefresh()
    }

    fun setOnRefreshListener(listener: OnRefreshListener) {
        this.mListener = listener
    }

    interface OnRefreshListener {
        fun onRefresh()
    }

    fun isRefreshing(): Boolean {
        return isRefreshing
    }
}
