package com.steven.movieapp.ui

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.utils.StatusBarUtils
import com.wingsofts.dragphotoview.DragPhotoView
import kotlinx.android.synthetic.main.activity_preview_photo.*
import kotlinx.android.synthetic.main.load_view.*

class PreviewPhotoActivity : BaseActivity(), DragPhotoView.OnExitListener {


    private val photoUrl by lazy {
        intent.getStringExtra("photo_url")
    }
    private val name: String by lazy {
        intent.getStringExtra("name")
    }

    override fun getLayoutId(): Int = R.layout.activity_preview_photo


    override fun initView() {
        StatusBarUtils.statusBarTintColor(this, ContextCompat.getColor(this, android.R.color.black))
        supportActionBar?.apply { this.title = name }
        Glide.with(this)
            .load(photoUrl)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    iv_actor.setImageBitmap((resource as BitmapDrawable).bitmap)
                    load_view.visibility = View.GONE
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        iv_actor.setOnExitListener(this)
    }

    override fun onExit(p0: DragPhotoView?, p1: Float, p2: Float, p3: Float, p4: Float) {
        finish()
    }
}
