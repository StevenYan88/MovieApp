package com.steven.movieapp.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.RelativeLayout

/**
 * Description:
 * Data：2/25/2019-4:04 PM
 * @author yanzhiwen
 */
class DragRelativeLayout : RelativeLayout {
    private val mDragIndex: Float = 0.35f
    private var mFingerDownY: Int = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN ->
                mFingerDownY = ev.rawY.toInt()

        }

        return super.dispatchTouchEvent(ev)
    }

    private var distanceY = 0

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollUp()) {
                    return super.onInterceptTouchEvent(ev)
                }
                distanceY = ((ev.rawY.toInt() - mFingerDownY) * mDragIndex).toInt()
                if (distanceY > 0)
                    return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                if (canScrollUp()) {
                    return super.onTouchEvent(ev)

                }
                distanceY = ((ev.rawY.toInt() - mFingerDownY) * mDragIndex).toInt()
                setMarginTop(distanceY)
            }
            MotionEvent.ACTION_UP -> {
                setMarginTop(0)
            }
        }
        return super.onTouchEvent(ev)

    }

    private fun canScrollUp(): Boolean {
        return this.canScrollVertically(-1)
    }

    private fun canScrollBottom(): Boolean {
        return this.canScrollVertically(1)
    }


    private fun setMarginTop(topMargin: Int) {
        val layoutParams = this.layoutParams as FrameLayout.LayoutParams
        if (topMargin == 0) {
            val animator = ObjectAnimator.ofFloat(distanceY.toFloat(), 0f)
                .setDuration(distanceY.toLong())
            animator.addUpdateListener { animation ->
                val currentTopMargin = animation.animatedValue as Float
                layoutParams.topMargin = currentTopMargin.toInt()
                setLayoutParams(layoutParams)
            }
            animator.start()
        } else {
            layoutParams.topMargin = topMargin
            setLayoutParams(layoutParams)

        }
    }
}