package com.steven.movieapp.ui

import com.google.android.material.appbar.AppBarLayout
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.utils.ShareUtil
import kotlinx.android.synthetic.main.activity_about_app.*
import kotlinx.android.synthetic.main.content_about_app.*
import kotlin.math.absoluteValue

class AboutAppActivity : BaseActivity() {

    private enum class CollapsingToolbarLayoutState {
        EXPANDED, COLLAPSED, INTERMEDIATE
    }

    private var state = CollapsingToolbarLayoutState.EXPANDED

    override fun getLayoutId() = R.layout.activity_about_app

    override fun initView() {
        supportActionBar?.apply { this.title = "" }
        fab.setOnClickListener {
            ShareUtil.share(this, content.text.toString())
        }

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (state != CollapsingToolbarLayoutState.EXPANDED) {
                state = CollapsingToolbarLayoutState.EXPANDED
                toolbar.title = ""
            } else if (verticalOffset.absoluteValue >= appBarLayout.totalScrollRange) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    state = CollapsingToolbarLayoutState.COLLAPSED
                    toolbar.title = getString(R.string.about_app)
                }
            }
        })
    }
}
