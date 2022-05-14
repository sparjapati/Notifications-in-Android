package com.notificationsInAndroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.notificationsInAndroid.utils.Constants
import com.notificationsInAndroid.utils.Constants.TAG
import com.notificationsInAndroid.utils.toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            val message = intent?.getStringExtra(Constants.KEY_MESSAGE)!!
            message.toast(context!!)
        } catch (e: Exception) {
            Log.d(TAG, "onReceive: ${e.message}")
        }
    }
}