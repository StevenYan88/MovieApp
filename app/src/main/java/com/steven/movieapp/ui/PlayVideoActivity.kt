package com.steven.movieapp.ui

import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.steven.movieapp.R
import com.steven.movieapp.base.BaseActivity
import com.steven.movieapp.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_play_video.*
import java.util.*

class PlayVideoActivity : BaseActivity() {
    private lateinit var mMediaPlayer: MediaPlayer
    private var mDragging: Boolean = false
    private var mShowing: Boolean = false
    private var mVideoBarHeight: Int = 0
    private var mVideoControllerHeight: Int = 0

    private val mFormatBuilder by lazy {
        StringBuilder()
    }
    private val mFormatter by lazy {
        Formatter(mFormatBuilder, Locale.getDefault())
    }

    private val videoUrl by lazy {
        intent.getStringExtra("video_url")
    }

    private val title by lazy {
        intent.getStringExtra("title")
    }


    companion object {
        const val defaultTimeout = 3000
    }


    override fun getLayoutId(): Int = R.layout.activity_play_video

    override fun initView() {
        StatusBarUtil.statusBarTranslucent(this)
        movie_name.text = this.title
        video.setVideoURI(Uri.parse(videoUrl))
        video.setOnPreparedListener { mp ->
            this.mMediaPlayer = mp
            progressBar.visibility = View.GONE
            endTime.text = stringForTime(mp.duration)
            video.post(mShowProgress)
            mp.start()
        }

        iv_play.setOnClickListener {
            doPauseResume()
        }

        video.setOnClickListener {
            if (mShowing) {
                hide()
            } else {
                show(defaultTimeout)
            }
        }

        iv_next.setOnClickListener {
            Toast.makeText(this, "小小编正在开发中...", Toast.LENGTH_SHORT).show()
        }


        iv_back.setOnClickListener {
            finish()
        }
        video.viewTreeObserver.addOnGlobalLayoutListener {
            mVideoBarHeight = video_navigation_bar.height
            mVideoControllerHeight = video_controller.height
        }

        seekBar.setOnSeekBarChangeListener(mOnSeekBarChangeListener)

    }

    private val mShowProgress = object : Runnable {
        override fun run() {
            val pos = setProgress()
            if (!mDragging && mMediaPlayer.isPlaying) {
                video.postDelayed(this, 1000)
            }
        }
    }

    private val mOnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (!fromUser) {
                return
            }

            val duration = mMediaPlayer.duration.toLong()
            val newPosition = duration * progress / 100L
            mMediaPlayer.seekTo(newPosition.toInt())
            currentTime.text = stringForTime(newPosition.toInt())
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            mDragging = true
            video.removeCallbacks(mFadeOut)
            video.removeCallbacks(mShowProgress)

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            mDragging = false
            setProgress()
            video.postDelayed(mFadeOut, defaultTimeout.toLong())
            video.post(mShowProgress)

        }
    }

    private fun updatePausePlay() {
        if (mMediaPlayer.isPlaying) {
            iv_play.setImageResource(R.mipmap.ic_pause)
        } else {
            iv_play.setImageResource(R.mipmap.ic_play)
        }
    }

    private fun show(timeOut: Int) {
        mShowing = true
        video.postDelayed(mFadeOut, timeOut.toLong())
        video_navigation_bar.animate().translationY(0f).setDuration(500).start()
        video_controller.animate().translationY(0f).setDuration(500).start()
    }

    private fun hide() {
        mShowing = false
        video.removeCallbacks(mFadeOut)
        video_navigation_bar.animate().translationY(-mVideoBarHeight.toFloat()).setDuration(500).start()
        video_controller.animate().translationY(mVideoControllerHeight.toFloat()).setDuration(500).start()

    }

    private val mFadeOut = Runnable {
        mShowing = false
        video_navigation_bar.animate().translationY(-mVideoBarHeight.toFloat()).setDuration(500).start()
        video_controller.animate().translationY(mVideoControllerHeight.toFloat()).setDuration(500).start()
    }

    private fun setProgress(): Int {
        val position = mMediaPlayer.currentPosition
        val duration = mMediaPlayer.duration
        if (duration > 0) {
            val pos = 100 * position / duration
            seekBar.progress = pos
        }
        endTime.text = stringForTime(duration)
        currentTime.text = stringForTime(position)
        return position
    }

    private fun doPauseResume() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
        } else {
            mMediaPlayer.start()
        }
        updatePausePlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        video.removeCallbacks(mShowProgress)
        video.removeCallbacks(mFadeOut)
        video.suspend()
    }


    private fun stringForTime(timeMs: Int): String {
        val totalSeconds = timeMs / 1000

        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600

        mFormatBuilder.setLength(0)
        return if (hours > 0) {
            mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            mFormatter.format("%02d:%02d", minutes, seconds).toString()
        }
    }
}
