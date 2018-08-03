package com.afflyas.fwcwallpapers.utils

import android.content.Context
import android.graphics.Bitmap
import com.afflyas.fwcwallpapers.core.MainActivity
import android.util.DisplayMetrics
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.content.Context.WINDOW_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.view.WindowManager
import android.view.Display






object WallpaperHelper {

    fun cropBitmapFromCenterAndScreenSize(mainActivity: MainActivity, bitmap: Bitmap): Bitmap {
        var bitmap = bitmap
        val bitmap_width = bitmap.width.toFloat()
        val bitmap_height = bitmap
                .height.toFloat()

        val displayMetrics = DisplayMetrics()
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val screenWidth = displayMetrics.widthPixels

        val bitmap_ratio = bitmap_width / bitmap_height
        val screen_ratio = screenWidth / screenHeight
        val bitmapNewWidth: Int
        val bitmapNewHeight: Int

        if (screen_ratio > bitmap_ratio) {
            bitmapNewWidth = screenWidth
            bitmapNewHeight = (bitmapNewWidth / bitmap_ratio).toInt()
        } else {
            bitmapNewHeight = screenHeight
            bitmapNewWidth = (bitmapNewHeight * bitmap_ratio).toInt()
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, bitmapNewWidth,
                bitmapNewHeight, true)

        val bitmapGapX: Int
        val bitmapGapY: Int
        bitmapGapX = ((bitmapNewWidth - screenWidth) / 2.0f).toInt()
        bitmapGapY = ((bitmapNewHeight - screenHeight) / 2.0f).toInt()

        bitmap = Bitmap.createBitmap(bitmap, bitmapGapX, bitmapGapY,
                screenWidth, screenHeight)
        return bitmap
    }
}