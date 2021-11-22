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
class LoadRefreshRecyclerView : RefreshRecyclerView {
    private var mLoadViewCreator: LoadViewCreator? = null
    private var mLoadView: View? = null
    private var mFingerDownY: Int = 0
    private var mLoadViewHeight: Int = 0
    private val mDragIndex: Float = 0.35f
    private var mCurrentDrag: Boolean = false
    private var mCurrentLoadStatus: Int =
        LOAD_STATUS_NORMAL
    private var mListener: OnLoadListener? = null

    companion object {
        //默认状态
        private const val LOAD_STATUS_NORMAL = 0x0011

        //上滑加载状态
        const val LOAD_STATUS_PULL_DOWN_REFRESH = 0x0022

        //松开加载状态
        const val LOAD_STATUS_LOOSEN_LOADING = 0x0033

        //正在刷加载状态
        private const val LOAD_STATUS_LOADING = 0x0044
    }

    private var isLoading: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        super.setAdapter(adapter)
        addLoadView()
    }

    fun addLoadViewCreator(loadViewCreator: LoadViewCreator) {
        this.mLoadViewCreator = loadViewCreator
        mLoadView = mLoadViewCreator!!.getLoadView(context, this)
    }

    private fun addLoadView() {
        val adapter = adapter
        if (adapter != null && mLoadViewCreator != null) {
            if (mLoadView != null) {
                addFooterView(mLoadView!!)
            }
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN ->
                mFingerDownY = ev.rawY.toInt()
            MotionEvent.ACTION_UP -> if (mCurrentDrag) {
                restoreLoadView()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun restoreLoadView() {
        if (mLoadView == null) return
        val currentBottomMargin = (mLoadView!!.layoutParams as MarginLayoutParams).bottomMargin
        val finalBottomMargin = 0
        if (mCurrentLoadStatus == LOAD_STATUS_LOOSEN_LOADING) {
            mCurrentLoadStatus =
                LOAD_STATUS_LOADING
            mLoadViewCreator!!.onLoading()

            mListener?.apply {
                onLoad()
            }

        }
        val distance = currentBottomMargin - finalBottomMargin

        val animator =
            ObjectAnimator.ofFloat(currentBottomMargin.toFloat(), finalBottomMargin.toFloat())
                .setDuration(distance.toLong())
        animator.addUpdateListener { animation ->
            @Suppress("NAME_SHADOWING")
            val currentBottomMargin = animation.animatedValue as Float
            setLoadViewMarginBottom(currentBottomMargin.toInt())
        }
        animator.start()
        mCurrentDrag = false
    }


    /**
     * 设置刷新topMargin
     */
    private fun setLoadViewMarginBottom(marginBottom: Int) {
        var bottomMargin = marginBottom
        val params = mLoadView!!.layoutParams as MarginLayoutParams
        if (marginBottom < 0) {
            bottomMargin = 0
        }
        params.bottomMargin = bottomMargin
        mLoadView!!.layoutParams = params
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollDown() || mCurrentLoadStatus == LOAD_STATUS_LOADING || mLoadView == null || mLoadViewCreator == null) {
                    isLoading = false
                    return super.onTouchEvent(e)
                }

                mLoadViewHeight = mLoadView!!.measuredHeight

                if (mCurrentDrag) scrollToPosition(adapter!!.itemCount - 1)
                //不断的上滑
                val distanceY = ((e.rawY - mFingerDownY) * mDragIndex).toInt()
                if (distanceY < 0) {
                    setLoadViewMarginBottom(-distanceY)
                    updateLoadStatus(-distanceY)
                    mCurrentDrag = true
                    isLoading = true
                    return false
                }
            }
        }
        return super.onTouchEvent(e)
    }

    private fun updateLoadStatus(distanceY: Int) {
        if (mLoadViewCreator == null) return
        mCurrentLoadStatus = when {
            distanceY <= 0 -> LOAD_STATUS_NORMAL
            distanceY < mLoadViewHeight -> LOAD_STATUS_PULL_DOWN_REFRESH
            else -> LOAD_STATUS_LOOSEN_LOADING
        }
        mLoadViewCreator!!.onPull(distanceY, mLoadViewHeight, mCurrentLoadStatus)
    }

    private fun canScrollDown(): Boolean {
        return this.canScrollVertically(1)
    }

    fun onStopLoad() {
        if (mLoadViewCreator == null) return
        mCurrentLoadStatus = LOAD_STATUS_NORMAL
        restoreLoadView()
        mLoadViewCreator!!.onStopLoad()
    }

    fun setOnLoadListener(listener: OnLoadListener) {
        this.mListener = listener
    }

    interface OnLoadListener {
        fun onLoad()
    }

    fun isLoading(): Boolean {
        return isLoading
    }
}
