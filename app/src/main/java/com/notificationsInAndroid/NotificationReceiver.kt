package com.notificationsInAndroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.notificationsInAndroid.utils.toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent!!.getStringExtra(Constants.KEY_MESSAGE)!!
        message.toast(context!!)
    }
}