package ru.dmitry.belyaev.serviceexample

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class CountService : Service() {

    private lateinit var scheduledExecutorService: ScheduledExecutorService
    private lateinit var simpleReceiver: SimpleReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onBind(p0: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        Log.d("myLogs", "Service: onCreate")
        scheduledExecutorService = Executors.newScheduledThreadPool(1)
        simpleReceiver = SimpleReceiver()
        intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        registerReceiver(simpleReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("myLogs", "Service: onStartCommand")
        scheduledExecutorService.scheduleAtFixedRate({
            Log.d("myLogs", System.currentTimeMillis().toString())
        }, 1000, 4000, TimeUnit.MILLISECONDS)
        return START_STICKY
    }

    override fun onDestroy() {
        scheduledExecutorService.shutdownNow()
        unregisterReceiver(simpleReceiver)
        Log.d("myLogs", "Service: onDestroy")
    }
}
