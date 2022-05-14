package com.notificationsInAndroid

import android.app.Notification
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
        binding.btnChannel1.setOnClickListener {
            val notification = NotificationCompat.Builder(this, Constants.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentTitle(binding.etTitle.text)
                .setContentText(binding.etDesc.text)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
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