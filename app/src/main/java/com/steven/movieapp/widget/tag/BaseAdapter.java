package com.steven.movieapp.widget.tag;

import android.view.View;
import android.view.ViewGroup;

/**
 * Description:流式布局的Adapter
 * Data：12/28/2017-4:11 PM
 *
 * @author: yanzhiwen
 */
public abstract class BaseAdapter {
    private TagObserver mTagObserver;

    //有多少个条目
    protected abstract int getCount();

    //返回当前的View
    protected abstract View getView(int position, ViewGroup parent);

    //观察者设计模式及时更新
    public void registerDataSetObserver(TagObserver observer) {
        this.mTagObserver = observer;

    }

    public void unregisterDataSetObserver(TagObserver observer) {

    }

    public void addView(View view) {
        if (mTagObserver != null) {
            mTagObserver.addView(view);
        }
    }

    public void removeView(int position) {
        if (mTagObserver != null) {
            mTagObserver.removeView(position);
        }
    }
}
