package com.steven.movieapp.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * Description:
 * Data：2/27/2019-3:40 PM
 * @author yanzhiwen
 */
class StatusBarUtil {
    companion object {
        /**
         * 设置状态栏的颜色
         */
        @TargetApi(19)
        fun statusBarTintColor(activity: Activity, color: Int) {
            // 代表 5.0 及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor = color
                return
            }

            // versionCode > 4.4  and versionCode < 5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                val androidContainer = activity.findViewById<View>(android.R.id.content) as ViewGroup
                // 留出高度 setFitsSystemWindows  true代表会调整布局，会把状态栏的高度留出来
                val contentView = androidContainer.getChildAt(0)
                if (contentView != null) {
                    contentView.fitsSystemWindows = true
                }
                // 在原来的位置上添加一个状态栏
                val statusBarView = createStatusBarView(activity)
                androidContainer.addView(statusBarView, 0)
                statusBarView.setBackgroundColor(color)
            }
        }

        /**
         * 创建一个需要填充statusBarView
         */
        private fun createStatusBarView(activity: Activity): View {
            val statusBarView = View(activity)
            val statusBarParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)
            )
            statusBarView.layoutParams = statusBarParams
            return statusBarView
        }

        /**
         * 获取状态栏的高度
         */
        private fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }


        /**
         * 状态栏透明,整个界面全屏
         */
        fun statusBarTranslucent(activity: Activity) {
            // 代表 5.0 及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val decorView = activity.window.decorView
                val option = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

                decorView.systemUiVisibility = option
                activity.window.statusBarColor = Color.TRANSPARENT
                activity.window.navigationBarColor = Color.TRANSPARENT
                return
            }

            // versionCode > 4.4  and versionCode < 5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }
}