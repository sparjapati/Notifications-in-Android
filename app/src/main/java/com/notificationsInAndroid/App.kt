package com.notificationsInAndroid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

// Step-01 create channels
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        // we need to create notifications channels in android version above OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                Constants.CHANNEL_1_ID,
                "channel1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = getString(R.string.channel1_desc)
            /*
             * we can change channel properties by its object
             * but once set, can't be changed
             * so have to uninstall and install again to change them
             */
            val channel2 = NotificationChannel(
                Constants.CHANNEL_2_ID,
                "channel2",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel2.description = getString(R.string.channel2_desc)
            //set channels
            val notificationService = getSystemService(NotificationManager::class.java)!!
            notificationService.createNotificationChannels(
                listOf(channel1, channel2)
            )
        }
    }
}