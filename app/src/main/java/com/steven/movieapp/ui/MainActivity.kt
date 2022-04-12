package com.steven.movieapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.steven.movieapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        nav_view.setOnClickListener {

        }
        about_app.setOnClickListener {

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            startActivity(Intent(this, AboutAppActivity::class.java))
        }
    }


    private fun setupFragment() {
        val tabMovies = resources.getStringArray(R.array.tab_movies)
        val fragments = ArrayList<Fragment>()
        fragments.add(MovieUsFragment.newInstance())
        fragments.add(NewMoviesFragment.newInstance())
        fragments.add(PraiseMovieFragment.newInstance())
        fragments.add(TheaterMovieFragment.newInstance())
        fragments.add(ComingMovieFragment.newInstance())
        fragments.add(Top250MovieFragment.newInstance())
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment = fragments[position]
        }
        //将TabLayout和ViewPager2进行绑定
        TabLayoutMediator(tab, viewPager) { tab, position ->
            tab.text = tabMovies[position]
        }.attach()

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
            R.id.about_app -> {
                startActivity(Intent(this, AboutAppActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
