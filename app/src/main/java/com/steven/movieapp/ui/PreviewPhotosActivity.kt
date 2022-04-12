package com.steven.movieapp.ui

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.bean.Photo
import com.steven.movieapp.utils.StatusBarUtils
import com.wingsofts.dragphotoview.DragPhotoView
import kotlinx.android.synthetic.main.activity_preview_photos.*


class PreviewPhotosActivity : BaseActivity(), DragPhotoView.OnExitListener {


    private val position: Int by lazy {
        intent.getIntExtra("position", 0)
    }
    private val photos: ArrayList<Photo> by lazy {
        intent.getParcelableArrayListExtra<Photo>("photos") ?: arrayListOf()
    }
    private val name: String by lazy {
        intent.getStringExtra("name") ?: ""

    }
    private val summary: String by lazy {
        intent.getStringExtra("summary") ?: ""

    }

    override fun getLayoutId() = R.layout.activity_preview_photos


    override fun initView() {
        StatusBarUtils.statusBarTintColor(this, ContextCompat.getColor(this, android.R.color.black))
        supportActionBar?.title = name
        actor_summary.text = summary
        index.text = String.format("%d/%d", position + 1, photos.size)
        viewPager.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val photoView = DragPhotoView(this@PreviewPhotosActivity)
                photoView.setOnExitListener(this@PreviewPhotosActivity)
                Glide.with(photoView).load(photos[position].image).fitCenter().into(photoView)
                container.addView(photoView)
                return photoView
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return photos.size
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
        viewPager.setCurrentItem(position, false)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                index.text = String.format("%d/%d", position + 1, photos.size)

            }
        })


    }

    override fun onExit(p0: DragPhotoView?, p1: Float, p2: Float, p3: Float, p4: Float) {
        finish()
    }
}
