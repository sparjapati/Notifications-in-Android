package com.notificationsInAndroid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Build
import com.notificationsInAndroid.utils.Constants
import com.notificationsInAndroid.utils.Constants.CHANNEL_GROUP_1_ID
import com.notificationsInAndroid.utils.Constants.CHANNEL_GROUP_2_ID

// Step-01 create channels
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        // we need to create notifications channels in android version above OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelGroup1 = NotificationChannelGroup(CHANNEL_GROUP_1_ID, "Group1")
            val channelGroup2 = NotificationChannelGroup(CHANNEL_GROUP_2_ID, "Group2")

            val channel1 = NotificationChannel(
                Constants.CHANNEL_1_ID,
                "channel1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = getString(R.string.channel1_desc)
            channel1.group = CHANNEL_GROUP_1_ID
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
            channel2.group = CHANNEL_GROUP_1_ID

            val channel3 = NotificationChannel(
                Constants.CHANNEL_3_ID,
                "channel3",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel3.description = getString(R.string.channel3_desc)
            channel3.group = CHANNEL_GROUP_2_ID

            val channel4 = NotificationChannel(
                Constants.CHANNEL_4_ID,
                "channel4",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel4.description = getString(R.string.channel4_desc)

            //set channels
            val notificationService = getSystemService(NotificationManager::class.java)!!
            notificationService.createNotificationChannelGroups(listOf(channelGroup1, channelGroup2))
            notificationService.createNotificationChannels(listOf(channel1, channel2, channel3, channel4))
        }
    }
}