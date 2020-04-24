package com.steven.movieapp

import android.app.Application
import android.preference.PreferenceManager
import com.steven.movieapp.utils.ThemeHelper

/**
 * @Description:
 * @Date: 2019/6/15
 * @Author: yanzhiwen
 */
class MovieApp :Application(){
    override fun onCreate() {
        super.onCreate()
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//        val themePref = sharedPreferences.getString("themePref", ThemeHelper.DEFAULT_MODE)
//        ThemeHelper.applyTheme(themePref)
        //setTheme(R.style.DarkAppTheme)
    }
}