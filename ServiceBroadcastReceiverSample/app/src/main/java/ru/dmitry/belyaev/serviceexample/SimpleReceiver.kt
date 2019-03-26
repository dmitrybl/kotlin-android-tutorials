package ru.dmitry.belyaev.serviceexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class SimpleReceiver : BroadcastReceiver() {

    companion object {
        val RECEIVER_MESSAGE = "ru.dmitry.belyaev.serviceexample:message_for_broadcast"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getStringExtra("time")?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(context, intent?.action?.toString(), Toast.LENGTH_SHORT).show()
    }

}