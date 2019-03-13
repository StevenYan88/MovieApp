package com.steven.movieapp.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Description:
 * Data：3/13/2019-11:55 AM
 * @author yanzhiwen
 */
object AppExecutors {
    private val diskIO: Executor by lazy {
        Executors.newSingleThreadExecutor()
    }
    private val networkIO: Executor by lazy {
        Executors.newFixedThreadPool(3)
    }
    private val mainThread by lazy {
        MainThreadExecutor()
    }

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }


}