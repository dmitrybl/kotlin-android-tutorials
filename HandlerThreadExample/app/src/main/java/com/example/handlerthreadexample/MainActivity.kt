package com.example.handlerthreadexample

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.asynktaskproject.R


class MainActivity : AppCompatActivity(), ImageProcessThread.Callback {

    private lateinit var mDoge: ImageView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mImageProcessThread: ImageProcessThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val performBtn = findViewById<Button>(R.id.btn_perform)
        mDoge = findViewById(R.id.iv_doge)
        mProgressBar = findViewById(R.id.progress)
        mImageProcessThread = ImageProcessThread("Background ")

        mImageProcessThread.start()

        mImageProcessThread.looper // onLooperPrepared()

        mImageProcessThread.setCallback(this)

        performBtn.setOnClickListener {
            val drawable = mDoge.drawable as BitmapDrawable
            mImageProcessThread.performOperation(drawable.bitmap)
        }

    }

    override fun sendProgress(process: Int) {
        mProgressBar.progress = process
    }

    override fun onCompleted(bitmap: Bitmap) {
        mDoge.setImageBitmap(bitmap)
    }

    override fun onDestroy() {
        mImageProcessThread.quit()
        super.onDestroy()
    }

}
