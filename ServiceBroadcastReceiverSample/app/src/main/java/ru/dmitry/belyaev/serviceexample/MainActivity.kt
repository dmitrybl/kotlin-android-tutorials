package ru.dmitry.belyaev.serviceexample

import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnStartService: Button
    private lateinit var btnStopService: Button
    private lateinit var btnSendBroadcast: Button

    private lateinit var simpleReceiver: SimpleReceiver
    private lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartService = findViewById(R.id.start_service)
        btnStartService.setOnClickListener {
            val intent = Intent(this@MainActivity, CountService::class.java)
            startService(intent)
        }

        btnStopService = findViewById(R.id.stop_service)
        btnStopService.setOnClickListener {
            val intent = Intent(this@MainActivity, CountService::class.java)
            stopService(intent)
        }

        btnSendBroadcast = findViewById(R.id.send_broadcast)
        btnSendBroadcast.setOnClickListener {
            val intent = Intent(SimpleReceiver.RECEIVER_MESSAGE)
            intent.putExtra("time", System.currentTimeMillis().toString())
            sendBroadcast(intent)
        }

        simpleReceiver = SimpleReceiver()
        intentFilter = IntentFilter(SimpleReceiver.RECEIVER_MESSAGE)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(simpleReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(simpleReceiver)
    }



}
