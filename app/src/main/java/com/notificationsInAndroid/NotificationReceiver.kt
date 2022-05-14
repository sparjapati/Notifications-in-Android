package com.notificationsInAndroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.RemoteInput
import com.notificationsInAndroid.utils.Constants
import com.notificationsInAndroid.utils.Constants.TAG

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val replyText = remoteInput.getCharSequence(Constants.KEY_REMOTE_INPUT)!!
            val message = Message(replyText, null)
//            Log.d(TAG, "onReceive: $message")
            MainActivity.MESSAGES.add(message)
            MainActivity.MESSAGES.add(Message("fusvfhsv", "abc"))
            MainActivity.updateNotification(context!!)
        } else {
            Log.d(TAG, "onReceive: null intent")
        }
    }
}