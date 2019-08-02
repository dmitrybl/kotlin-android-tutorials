package com.example.handlerthreadexample

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import java.lang.IllegalStateException

class ImageProcessThread(name: String) : HandlerThread(name) {

    private val MESSAGE_CONVERT = 0

    private val PERCENT = 100

    private val PARTS_COUNT = 50

    private val PART_SIZE = PERCENT / PARTS_COUNT

    private var mMainHandler: Handler? = null

    private var mBackgroundHandler: Handler? = null

    private var mCallback: Callback? = null


    fun setCallback(callback: Callback) {
        mCallback = callback
    }


    @SuppressLint("HandlerLeak")
    override fun onLooperPrepared() {
        mMainHandler = Handler(Looper.getMainLooper())

        mBackgroundHandler = object: Handler() {
            override fun handleMessage(msg: Message) {
                when(msg.what) {
                    MESSAGE_CONVERT -> {
                        val bitmap = msg.obj as Bitmap
                        processBitmap(bitmap)
                        try {
                            msg.recycle()
                        } catch(e: IllegalStateException) {
                            removeMessages(msg.what)
                        }
                    }
                }
            }
        }
    }

    private fun processBitmap(bitmap: Bitmap) {

        val h = bitmap.height

        val w = bitmap.width

        val pixels = IntArray(h * w)

        bitmap.getPixels(pixels, 0, w, 0, 0, w, h)

        val progress = IntArray(1)

        for (i in 0 until h * w) {

            val hex = String.format("#%06X", 0xFFFFFF and pixels[i])

            val R = hex.substring(1, 3)

            val G = hex.substring(3, 5)

            val B = hex.substring(5)

            val mess = B + R + G

            pixels[i] = Integer.parseInt(mess, 16)

            val part = w * h / PARTS_COUNT



            if (i % part == 0) {

                progress[0] = i / part * PART_SIZE

                mMainHandler!!.post {
                    mCallback!!.sendProgress(progress[0])
                }

            }

        }

        val result = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.RGB_565)

        mMainHandler!!.post {
            mCallback!!.onCompleted(result)
        }


    }

    fun performOperation(inputData: Bitmap) {
        mBackgroundHandler!!.obtainMessage(MESSAGE_CONVERT, inputData)
            .sendToTarget()
    }


    interface Callback {
        fun sendProgress(process: Int)
        fun onCompleted(bitmap: Bitmap)
    }

}