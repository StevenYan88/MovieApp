package com.steven.movieapp.widget.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * Data：2/22/2019-9:54 AM
 * @author yanzhiwen
 */
class DividerItemDecoration(context: Context, drawableId: Int, private var orientation: Int) :
    RecyclerView.ItemDecoration() {

    companion object {
        private const val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
        private const val VERTICAL_LIST = LinearLayoutManager.VERTICAL

    }

    private val mDrawable: Drawable = ContextCompat.getDrawable(context, drawableId)!!

    init {

        setOrientation(orientation)
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (orientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val params = childView.layoutParams as RecyclerView.LayoutParams
            val top = childView.bottom + params.bottomMargin
            val bottom = top + mDrawable.intrinsicHeight
            mDrawable.setBounds(left, top, right, bottom)
            mDrawable.draw(canvas)
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {

        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val params = childView.layoutParams as RecyclerView.LayoutParams
            val left = childView.left + params.leftMargin
            val right = left + mDrawable.intrinsicHeight
            mDrawable.setBounds(left, top, right, bottom)
            mDrawable.draw(canvas)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDrawable.intrinsicHeight)
        } else {
            outRect.set(0, 0, mDrawable.intrinsicWidth, 0)
        }
    }
}