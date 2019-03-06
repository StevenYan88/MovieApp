package com.steven.movieapp.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Description:
 * Data：1/30/2019-11:29 AM
 * @author yanzhiwen
 */
class TranslationBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior(context, attrs) {
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    private var isOut: Boolean = false

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        if (dyConsumed > 0) {
            if (!isOut) {
                val translationY =
                    (child.layoutParams as CoordinatorLayout.LayoutParams).bottomMargin + child.measuredHeight
                child.animate().translationY(translationY.toFloat()).setDuration(500).start()
                isOut = true
            }
        } else {
            if (isOut) {
                child.animate().translationY(0f).setDuration(500).start()
                isOut = false
            }
        }
    }
}
