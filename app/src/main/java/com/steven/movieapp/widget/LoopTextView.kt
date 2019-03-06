package com.steven.movieapp.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.steven.movieapp.R


/**
 * Description:
 * Data：1/29/2019-4:41 PM
 * @author yanzhiwen
 */
class LoopTextView : FrameLayout {
    private lateinit var textList: ArrayList<String>
    private lateinit var anim_out: Animation
    private lateinit var anim_in: Animation
    private lateinit var tv_out: TextView
    private lateinit var tv_in: TextView
    private var currentIndex: Int = 0
//    private var lastTimeMillis: Long = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initFrame()
        initAnimation()
    }

    private fun initFrame() {
        tv_out = newTextView()
        tv_in = newTextView()
        addView(tv_out)
        addView(tv_in)
    }

    private fun initAnimation() {
        anim_out = newAnimation(0f, -1f)
        anim_in = newAnimation(1f, 0f)
        anim_in.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationRepeat(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                //updateTextAndPlayAnimationWithCheck()
                updateTextAndPlayAnimation()

            }
        })

    }

//    private fun updateTextAndPlayAnimationWithCheck() {
//        if (System.currentTimeMillis() - lastTimeMillis < 1000) {
//            return
//        }
//        lastTimeMillis = System.currentTimeMillis()
//    }

    private fun newAnimation(fromYValue: Float, fromToValue: Float): Animation {
        val animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, fromYValue, Animation.RELATIVE_TO_SELF, fromToValue
        )
        animation.duration = 888
        animation.startOffset = 3600
        animation.interpolator = DecelerateInterpolator()
        return animation
    }


    private fun newTextView(): TextView {
        val textView = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL
        )
        textView.layoutParams = layoutParams
        textView.ellipsize = TextUtils.TruncateAt.END
        textView.compoundDrawablePadding = 8
        textView.maxLines = 1
        textView.setTextColor(ContextCompat.getColor(context, R.color.color_black))
        textView.setCompoundDrawables(loadDrawable(), null, null, null)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.gravity = Gravity.CENTER_VERTICAL
        return textView
    }


    private fun updateTextAndPlayAnimation() {
        if (this.currentIndex % 2 == 0) {
            updateText(tv_out)
            tv_in.startAnimation(anim_out)
            tv_out.startAnimation(anim_in)
            this.bringChildToFront(tv_in)
        } else {
            updateText(tv_in)
            tv_out.startAnimation(anim_out)
            tv_in.startAnimation(anim_in)
            this.bringChildToFront(tv_out)
        }
    }

    private fun updateText(textView: TextView) {
        val text = getNextText()
        if (!TextUtils.isEmpty(text)) {
            textView.text = text
        }
    }


    fun setTextList(textList: ArrayList<String>) {
        this.textList = textList
        currentIndex = 0
        updateText(tv_out)
        updateTextAndPlayAnimation()
    }

    /**
     * 将资源图片转换为Drawable对象
     * @return drawable
     */
    private fun loadDrawable(): Drawable {
        val drawable = ContextCompat.getDrawable(context, R.mipmap.ic_search)
        drawable!!.setBounds(0, 0, drawable.minimumWidth - 8, drawable.minimumHeight - 8)
        return drawable
    }


    /**
     * 获取下一条消息
     * @return
     */
    private fun getNextText(): String? {
        return if (isListEmpty(textList)) null else textList[currentIndex++ % textList.size]
    }

    private fun isListEmpty(textList: ArrayList<String>): Boolean {
        return textList.isEmpty()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        anim_in.cancel()
        anim_out.cancel()
    }
}
