package com.steven.movieapp.widget

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.steven.movieapp.R

/**
 * Description:
 * Data：3/20/2019-10:47 AM
 * @author yanzhiwen
 */
class StatusView : FrameLayout {

    @LayoutRes
    private var loadViewId = R.layout.load_view
    @LayoutRes
    private var errorViewId = R.layout.load_fail_view
    @LayoutRes
    private var noNetworkViewId = R.layout.no_network_view

    private var currentView: View

    // 状态布局 View 缓存集合
    private val viewArray = SparseArray<View>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StatusView)
        attributes.apply {
            loadViewId = getResourceId(R.styleable.StatusView_loadView, loadViewId)
            errorViewId = getResourceId(R.styleable.StatusView_loadView, errorViewId)
            noNetworkViewId = getResourceId(R.styleable.StatusView_loadView, noNetworkViewId)
        }
        attributes.recycle()

        currentView = LayoutInflater.from(context).inflate(loadViewId, null)
    }


    fun showLoadView() {
        switchStatusView(loadViewId)
    }

    fun showErrorView() {
        switchStatusView(errorViewId)
    }

    fun showNoNetworkView() {
        switchStatusView(noNetworkViewId)
    }


    private fun switchStatusView(layoutId: Int) {
        val statusView = generateStatusView(layoutId)
        statusView?.let {
            switchStatusView(it)
        }
    }


    private fun switchStatusView(statusView: View) {
        if (statusView == currentView) {
            return
        }
        removeView(currentView)
        currentView = statusView
        addView(currentView)
    }

    /**
     * 根据布局文件 Id 得到对应的 View，并设置控件属性、绑定接口
     */
    private fun generateStatusView(@LayoutRes layoutId: Int): View? {
        var statusView: View? = viewArray.get(layoutId)
        if (statusView == null) {
            statusView = LayoutInflater.from(context).inflate(layoutId, null)
            viewArray.put(layoutId, statusView)
            configStatusView(layoutId, statusView)

        }
        return statusView
    }

    private fun configStatusView(layoutId: Int, statusView: View) {
        if (layoutId == noNetworkViewId || layoutId == errorViewId) {
            statusView.findViewById<View>(R.id.tv_reLoad).setOnClickListener {
                /*listener?.apply {
                    onClick()
                }*/
                listener?.onClick()
            }
        }
    }

    private var listener: OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    interface OnClickListener {
        fun onClick()
    }
}