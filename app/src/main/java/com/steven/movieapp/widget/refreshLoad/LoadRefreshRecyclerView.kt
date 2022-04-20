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
    private var loadViewCreator: LoadViewCreator? = null
    private var loadView: View? = null
    private var fingerDownY: Int = 0
    private var loadViewHeight: Int = 0
    private val dragIndex: Float = 0.35f
    private var currentDrag: Boolean = false
    private var currentLoadStatus: Int = LOAD_STATUS_NORMAL
    private var onLoadListener: OnLoadListener? = null

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
        this.loadViewCreator = loadViewCreator
        loadView = this.loadViewCreator!!.getLoadView(context, this)
    }

    private fun addLoadView() {
        val adapter = adapter
        if (adapter != null && loadViewCreator != null && loadView != null) {
            addFooterView(loadView!!)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN ->
                fingerDownY = ev.rawY.toInt()
            MotionEvent.ACTION_UP -> if (currentDrag) {
                restoreLoadView()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun restoreLoadView() {
        if (loadView == null) return
        val currentBottomMargin = (loadView!!.layoutParams as MarginLayoutParams).bottomMargin
        val finalBottomMargin = 0
        if (currentLoadStatus == LOAD_STATUS_LOOSEN_LOADING) {
            currentLoadStatus =
                LOAD_STATUS_LOADING
            loadViewCreator!!.onLoading()

            onLoadListener?.apply {
                onLoad()
            }

        }
        val distance = currentBottomMargin - finalBottomMargin

        val animator =
            ObjectAnimator.ofFloat(currentBottomMargin.toFloat(), finalBottomMargin.toFloat())
                .setDuration(distance.toLong())
        animator.addUpdateListener { animation ->
            val currentBottomMargin = animation.animatedValue as Float
            setLoadViewMarginBottom(currentBottomMargin.toInt())
        }
        animator.start()
        currentDrag = false
    }

    /**
     * 设置刷新topMargin
     */
    private fun setLoadViewMarginBottom(marginBottom: Int) {
        var bottomMargin = marginBottom
        val params = loadView!!.layoutParams as MarginLayoutParams
        if (marginBottom < 0) {
            bottomMargin = 0
        }
        params.bottomMargin = bottomMargin
        loadView!!.layoutParams = params
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollDown() || currentLoadStatus == LOAD_STATUS_LOADING || loadView == null || loadViewCreator == null) {
                    isLoading = false
                    return super.onTouchEvent(e)
                }

                loadViewHeight = loadView!!.measuredHeight
                if (currentDrag) scrollToPosition(adapter!!.itemCount - 1)
                //不断的上滑
                val distanceY = ((e.rawY - fingerDownY) * dragIndex).toInt()
                if (distanceY < 0) {
                    setLoadViewMarginBottom(-distanceY)
                    updateLoadStatus(-distanceY)
                    currentDrag = true
                    isLoading = true
                    return false
                }
            }
        }
        return super.onTouchEvent(e)
    }

    private fun updateLoadStatus(distanceY: Int) {
        if (loadViewCreator == null) return
        currentLoadStatus = when {
            distanceY <= 0 -> LOAD_STATUS_NORMAL
            distanceY < loadViewHeight -> LOAD_STATUS_PULL_DOWN_REFRESH
            else -> LOAD_STATUS_LOOSEN_LOADING
        }
        loadViewCreator!!.onPull(distanceY, loadViewHeight, currentLoadStatus)
    }

    private fun canScrollDown(): Boolean {
        return this.canScrollVertically(1)
    }

    fun onStopLoad() {
        if (loadViewCreator == null) return
        currentLoadStatus = LOAD_STATUS_NORMAL
        restoreLoadView()
        loadViewCreator!!.onStopLoad()
    }

    fun setOnLoadListener(listener: OnLoadListener) {
        this.onLoadListener = listener
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    interface OnLoadListener {
        fun onLoad()
    }
}
