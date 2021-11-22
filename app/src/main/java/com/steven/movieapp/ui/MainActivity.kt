package com.steven.movieapp.ui

import android.content.Intent
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity() {


    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        setSupportActionBar(toolbar)
        setupFragment()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.syncState()

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerOpened(drawerView: View) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })
        nav_view.setOnClickListener {

        }
        sw_night.setOnCheckedChangeListener { _, isChecked ->

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            if (isChecked) {
                setTheme(R.style.DarkAppTheme)
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                sharedPreferences.edit().putString("themePref",ThemeHelper.DARK_MODE).commit()
            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                sharedPreferences.edit().putString("themePref",ThemeHelper.LIGHT_MODE).commit()

                setTheme(R.style.AppTheme)

            }


        }
        about_app.setOnClickListener {

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            startActivity(Intent(this, AboutAppActivity::class.java))

        }

    }

    private fun setupFragment() {
        val movies = resources.getStringArray(R.array.tab_movies)
        val fragments = ArrayList<Fragment>()
        fragments.add(TheaterMovieFragment.newInstance())
        fragments.add(ComingMovieFragment.newInstance())
        fragments.add(WeeklyMovieFragment.newInstance())
        fragments.add(UsMovieFragment.newInstance())
        fragments.add(NewMoviesFragment.newInstance())
        fragments.add(Top250MovieFragment.newInstance())

        viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment = fragments[position]

            override fun getPageTitle(position: Int): CharSequence? = movies[position]


            override fun getCount(): Int = movies.size
        }
        viewPager.offscreenPageLimit = fragments.size - 1
        tab.setupWithViewPager(viewPager)
        loop_movie_name.setOnClickListener {
            val intent = Intent(this, SearchMovieActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.about_app -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
