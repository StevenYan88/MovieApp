package com.steven.movieapp.widget

import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView


/**
 * Description:
 * Data：2/25/2019-2:20 PM
 * @author yanzhiwen
 */
class CustomLinkMovementMethod : LinkMovementMethod() {
    private var mPressedSpan: ClickableSpan? = null


    companion object {
        val instance: CustomLinkMovementMethod by lazy {
            CustomLinkMovementMethod()
        }
    }

    override fun onTouchEvent(textView: TextView, spannable: Spannable, event: MotionEvent): Boolean {
        val action = event.action
        //以下 基本copy 的LinkMovementMethod
        if (action == MotionEvent.ACTION_UP ||
            action == MotionEvent.ACTION_DOWN
        ) {
            mPressedSpan = getPressedSpan(textView, spannable, event);
            if (mPressedSpan != null) {
                if (action == MotionEvent.ACTION_UP) {
                    mPressedSpan!!.onClick(textView)
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(
                        spannable,
                        spannable.getSpanStart(mPressedSpan),
                        spannable.getSpanEnd(mPressedSpan)
                    )
                }

            } else {
                Selection.removeSelection(spannable)
            }

        } else {
            mPressedSpan = null
            Selection.removeSelection(spannable)
        }

        return mPressedSpan != null

    }


    private fun getPressedSpan(textView: TextView, spannable: Spannable, event: MotionEvent): ClickableSpan? {

        //触摸点相对于其所在组件原点的x坐标
        var x = event.x.toInt()
        var y = event.y.toInt()
        //获取触摸点距离可绘制区域(即减去padding后的区域)边界的距离
        x -= textView.totalPaddingLeft
        y -= textView.totalPaddingTop
        //获取触摸点相对于屏幕原点的偏移量，一般为0，除非可以滑动。其中view.getScrollX()获取的不是当前View相对于屏幕原点的偏移量，
        // 而是当前View可绘制区域（显示区域，-padding）相对于屏幕原点在Y轴上的偏移量
        x += textView.scrollX
        y += textView.scrollY

        val layout = textView.layout
        //该点击位置的行号----触摸点在textView中垂直方向上的行数值。参数是触摸点在Y轴上的偏移量
        val line = layout.getLineForVertical(y)
        //该点击位置的字符索引-----触摸点在某一行水平方向上的偏移量。参数分别是：该行行数值
        // 和 触摸点在该行X轴上的偏移量。此方法得到的该值会根据该行上的文字的多少而变化，并不是横向上的像素大小。
        val off = layout.getOffsetForHorizontal(line, x.toFloat())

        Log.e("文本", "line = $line  off = $off")
        //获取点击位置存在的ClickableSpan对象
        val link = spannable.getSpans<ClickableSpan>(off, off, ClickableSpan::class.java)
        var touchedSpan: ClickableSpan? = null
        if (link.isNotEmpty()) {
            touchedSpan = link[0]
        }
        return touchedSpan

    }

    fun isPressedSpan(): Boolean {
        return true
    }
}