package com.steven.movieapp.widget.tag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：12/28/2017-11:49 AM
 *
 * @author: yanzhiwen
 */
public class TagLayout extends ViewGroup {
    private List<List<View>> mChildViews = new ArrayList<>();
    private BaseAdapter mAdapter;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //清空集合
        mChildViews.clear();
        //获取宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高度需要计算
        int height = getPaddingTop() + getPaddingBottom();
        //每行的宽度
        int lineWidth = getPaddingLeft();
        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);
        int childCount = getChildCount();
        //子View高度不一致的情况
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            //for 循环测量子View
            View childView = getChildAt(i);
            //测量子View，会调用子View的onMeasure()方法
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //margin值 ViewGroup.LayoutParams没有 就用系统的MarginLayoutParam
            //想想LineaLayout为什么有 LineaLayout有自己的LayoutParams
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();
            //一行不够的情况需要换行  需要考虑margin 子View高度不一致的情况
            if ((lineWidth + childView.getMeasuredWidth() + childView.getPaddingLeft() + childView.getPaddingRight() +
                    params.leftMargin + params.rightMargin) > width) {
                //换行，累加高度height=自身的高度+自身的外边距+自身的内边距
                height += childView.getMeasuredHeight() + params.topMargin + params.bottomMargin +
                        childView.getPaddingTop() + childView.getPaddingBottom();
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
                //换行之后每行的宽度从getPaddingLeft开始
                lineWidth = getPaddingLeft();
            } else {
                //累加每个Tag的宽度，lineWidth=自身的宽度+自身的外边距+自身的内边距
                lineWidth += childView.getMeasuredWidth() + params.leftMargin + params.rightMargin
                        + childView.getPaddingLeft() + childView.getPaddingRight();
                //处理下View高度不一致的情况
                maxHeight = Math.max(childView.getMeasuredHeight() + params.topMargin + params.bottomMargin +
                        childView.getPaddingTop() + childView.getPaddingBottom(), maxHeight);
            }
            childViews.add(childView);
        }
        height += maxHeight;
        //指定自己宽度
        setMeasuredDimension(width, height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, right, top = getPaddingTop(), bottom = getPaddingBottom();
        //for 循环测量子View
        int maxHeight = 0;
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();
            for (View childView : childViews) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();
                left += params.leftMargin + childView.getPaddingLeft();
                int childTop = top + params.topMargin + childView.getPaddingTop();
                right = left + childView.getMeasuredWidth();
                bottom = childTop + getMeasuredHeight() + childView.getPaddingBottom();
                childView.layout(left, childTop, right, bottom);
                //叠加left
                left += childView.getMeasuredWidth() + params.rightMargin;
                int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin +
                        childView.getPaddingTop() + childView.getPaddingBottom();
                maxHeight = Math.max(maxHeight, childHeight);
            }
            //不断的叠加top
            top += maxHeight;

        }
    }


    private AdapterTagObserver mAdapterTagObserver;

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            //adapter是空的话抛空指针异常
            throw new NullPointerException("adapter not null");
        }
        if (adapter != null && mAdapterTagObserver != null) {
            this.mAdapterTagObserver = null;
        }
        if (adapter != null) {
            mAdapterTagObserver = new AdapterTagObserver();
            adapter.registerDataSetObserver(mAdapterTagObserver);
        }
        //清空所有子View
        removeAllViews();
        //mAdapter = null;
        mAdapter = adapter;
        int childCount = mAdapter.getCount();
        for (int i = 0; i < childCount; i++) {
            //通过位置获取View
            View childView = mAdapter.getView(i, this);
            addView(childView);
        }
    }


    private class AdapterTagObserver extends TagObserver {

        @Override
        public void addView(View view) {
            TagLayout.this.addView(view);
        }

        @Override
        public void removeView(int position) {
            TagLayout.this.removeViewAt(position);
        }
    }
}
