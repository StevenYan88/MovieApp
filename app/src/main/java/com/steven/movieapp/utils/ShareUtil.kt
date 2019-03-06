package com.steven.movieapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.ShareCompat

/**
 * Description:
 * Data：2019/2/26
 * Author:Steven
 */
class ShareUtil {
    companion object {

        fun share(context: Context, message: String) {
            val shareIntent = ShareCompat.IntentBuilder.from(context as Activity)
                .setText(message)
                .setType("text/plain")
                .createChooserIntent()
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    } else {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                    }
                }
            context.startActivity(shareIntent)
        }
    }
}
