package com.steven.movieapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.steven.movieapp.R


/**
 * Description:
 * Data：2/25/2019-9:51 AM
 * @author yanzhiwen
 */
class CollapsibleTextView : TextView {
    //最大显示的行数，其余的折叠
    var collapsedLines: Int
    //折叠时的后缀文字,eg.“展开”
    var collapsedText: String
    //展开时的后缀文字,eg.“收起”
    var expandedText: String

    var suffixColor: Int
    //默认显示3行
    private var defaultLines: Int = 3

    private var defaultCollapsedText: String = "【展开】"

    private var defaultExpandedText: String = "【收起】"

    //TextView的文本内容
    private lateinit var content: String
    //是否展开
    private var isNeedExpanded: Boolean = true
    //是否带省略点...
    private var isNeedEllipsis: Boolean = true

    private val tag: String = "..."

    private var onTextClickListener: OnTextClickListener? = null

    private var customLinkMovementMethod: CustomLinkMovementMethod? = null

    private var isDraw: Boolean = true

    private val mClickSpanListener = object : ClickableSpan() {
        override fun onClick(widget: View) {
            isNeedExpanded = !isNeedExpanded
            updateUI(isNeedExpanded)

        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            //点击区域下方是否有下划线。默认true，会像超链接一样显示下划线
            ds.isUnderlineText = false
            //取消选中文字背景色高亮显示
            highlightColor = Color.TRANSPARENT

        }

    }
    /**
     * TextView自身点击事件(除后缀外的部分)
     */
    private val selfClickSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            if (onTextClickListener != null)
                onTextClickListener!!.onTextClick()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }
    }


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CollapsibleTextView)
        collapsedLines =
            typedArray.getInteger(R.styleable.CollapsibleTextView_collapsedLines, defaultLines)
        collapsedText = typedArray.getString(R.styleable.CollapsibleTextView_collapsedText)
        expandedText = typedArray.getString(R.styleable.CollapsibleTextView_expandedText)
        suffixColor = typedArray.getColor(R.styleable.CollapsibleTextView_suffixColor, Color.RED)
        typedArray.recycle()


        if (TextUtils.isEmpty(collapsedText))
            collapsedText = defaultCollapsedText
        if (TextUtils.isEmpty(expandedText))
            expandedText = defaultExpandedText

        //若还需要对TextView的父容器进行点击事件设置，需要判断点击区域有没有clickspan，有自身消费，无则交由上层viewGroup处理。
        customLinkMovementMethod = CustomLinkMovementMethod.instance
        movementMethod = customLinkMovementMethod
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isDraw) {
            this.content = text.toString()
            if (lineCount > collapsedLines) {
                updateUI(isNeedExpanded)
            }
        }
    }

    private fun updateUI(isNeedExpanded: Boolean) {
        isDraw = true
        var temp = content
        val str: SpannableString
        var suffix = defaultCollapsedText
        if (collapsedLines < 1) {
            throw  RuntimeException("CollapsedLines must larger than 0")
        }
        //展开
        if (isNeedExpanded) {
            //第 collapsedLines 行打上省略点+后缀,padding不计算在内】
            val lineEnd = layout.getLineVisibleEnd(collapsedLines - 1)
            //带上省略点
            temp = if (isNeedEllipsis) {
                val newEnd = lineEnd - tag.length - suffix.length
                val end = if (newEnd > 0) {
                    newEnd
                } else lineEnd
                temp.substring(0, end) + tag
            } else {
                //不需要减去省略点大小
                val newEnd = lineEnd - suffix.length
                val end = if (newEnd > 0) {
                    newEnd
                } else lineEnd
                temp.substring(0, end)
            }
            str = SpannableString(temp + suffix)
        } else {
            suffix = defaultExpandedText
            str = SpannableString(temp + suffix)
        }
        //设置后缀点击事件
        str.setSpan(
            mClickSpanListener,
            temp.length,
            temp.length + suffix.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //设置后缀文字颜色
        str.setSpan(
            ForegroundColorSpan(suffixColor),
            temp.length,
            temp.length + suffix.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE

        )
        if (this.onTextClickListener != null) {
            //一定要判断。【一般自身点击与父容器点击只存其一。如果不判断，相当于整个TextView均含clickspan，不会再响应父容器点击事件】
            str.setSpan(
                selfClickSpan,
                0,
                temp.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        //设置自身点击事件【避免与自身点击事件冲突，采用剩余部位点击事件实现
        //一定要判断。,一般自身点击与父容器点击只存其一。如果不判断，相当于整个TextView均含clickspan，不会再响应父容器点击事件
        if (onTextClickListener != null) {
            str.setSpan(
                selfClickSpan,
                0,
                temp.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text = str
        isDraw = false

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val result = super.onTouchEvent(event)
        if (customLinkMovementMethod != null) {
            return customLinkMovementMethod!!.isPressedSpan()
        } else {

        }
        return result

    }
}

interface OnTextClickListener {
    fun onTextClick()

}