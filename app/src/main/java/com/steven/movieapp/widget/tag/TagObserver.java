package com.steven.movieapp.widget.tag;

import android.view.View;

/**
 * Description:
 * Data：3/14/2018-5:06 PM
 *
 * @author: yanzhiwen
 */
public abstract class TagObserver {
    /**
     * 添加Tag
     *
     * @param view
     */
    public abstract void addView(View view);

    /**
     * 移除Tag
     *
     * @param position
     */

    public abstract void removeView(int position);
}
