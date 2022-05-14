package com.notificationsInAndroid

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.notificationsInAndroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        // create intent for action in notification
        val intent = Intent(this, MainActivity::class.java)

        /*
        * this should not be executed now, this should be a pending intent
        * we can create a service or broadcast message using PendingIntent.method()
        * request code for handling the notification later
        */
        val contentIntent = PendingIntent.getActivity(this, 0, intent, 0)

        /*
        * Button in notification are called action Buttons
        * which also triggers pending intents
         */
        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra(Constants.KEY_MESSAGE, binding.etDesc.text)
        val actionIntent = PendingIntent.getBroadcast(this, 2, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        binding.btnChannel1.setOnClickListener {
            val notification = NotificationCompat.Builder(this, Constants.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(binding.etTitle.text)
                .setContentText(binding.etDesc.text)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.CYAN)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_one, "Toast", actionIntent)
                .setOnlyAlertOnce(true)
                .build()
            sendNotification(1, notification)
        }
        binding.btnChannel2.setOnClickListener {
            val notification = NotificationCompat.Builder(this, Constants.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(binding.etTitle.text)
                .setContentText(binding.etDesc.text)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()
            sendNotification(2, notification)
        }
    }

    private fun sendNotification(id: Int, notification: Notification) {
        // if we send same id again, it will override the previous one notification
        notificationManager.notify(id, notification)
    }
}