package com.notificationsInAndroid

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput
import androidx.core.graphics.drawable.IconCompat
import com.notificationsInAndroid.databinding.ActivityMainBinding
import com.notificationsInAndroid.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(this)
    }
    private val mediaSession: MediaSessionCompat by lazy {
        MediaSessionCompat(this, Constants.TAG)
    }

    companion object {
        val MESSAGES = ArrayList<Message>()

        @SuppressLint("NewApi")
        fun updateNotification(context: Context) {
            // create intent for action in notification
            val intent = Intent(context, MainActivity::class.java)

            /*
            * this should not be executed now, this should be a pending intent
            * we can create a service or broadcast message using PendingIntent.method()
            * request code for handling the notification later
            */
            val contentIntent = PendingIntent.getActivity(context, 0, intent, 0)

            /*
            * Button in notification are called action Buttons
            * which also triggers pending intents
             */
            val broadcastIntent = Intent(context, NotificationReceiver::class.java)
            val actionIntent = PendingIntent.getBroadcast(context, 2, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val remoteInput = RemoteInput.Builder(Constants.KEY_REMOTE_INPUT)
                //label -> hint in input-field
                .setLabel("your reply...")
                .build()

            val replyIntent = Intent(context, NotificationReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(context, 1, replyIntent, 0)
            val replyAction = NotificationCompat.Action.Builder(R.drawable.ic_one, "Reply", replyPendingIntent)
                .addRemoteInput(remoteInput).build()

//        // handle message notification in lower apis
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            replyIntent = Intent(this, MainActivity::class.java)
//            replyPendingIntent = PendingIntent.getBroadcast(this, 1, replyIntent, 0)
//        }

            val self = Person.Builder().setName("Me").build()
            val messageStyle = NotificationCompat.MessagingStyle(self)
            messageStyle.conversationTitle = "Group chat"

            MESSAGES.forEach { chat ->
                val message = NotificationCompat.MessagingStyle.Message(chat.text, chat.timeStamp, chat.sender)
                messageStyle.messages.add(message)
            }
            val notification = NotificationCompat.Builder(context, Constants.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messageStyle)
                .addAction(replyAction)
                .setColor(context.getColor(R.color.purple_500))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.CYAN)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_one, "Mark as Read", null)
                .setOnlyAlertOnce(true)
                .build()
            context.getSystemService(NotificationManager::class.java).notify(1, notification)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()

        MESSAGES.add(Message("Hello good morning, Sanjay!!", "abc"))
        MESSAGES.add(Message("Get up\uD83D\uDE18", "abc"))
        MESSAGES.add(Message("Haven't wake up till now??", "abc"))
        MESSAGES.add(Message("Just wake up", null))
    }

    @SuppressLint("NewApi")
    private fun setOnClickListener() {

        binding.btnChannel1.setOnClickListener {
            // create intent for action in notification
            val intent = Intent(this, MainActivity::class.java)

            /*
            * this should not be executed now, this should be a pending intent
            * we can create a service or broadcast message using PendingIntent.method()
            * request code for handling the notification later
            */
            val contentIntent = PendingIntent.getActivity(this, 0, intent, 0)

            val remoteInput = RemoteInput.Builder(Constants.KEY_REMOTE_INPUT)
                //label -> hint in input-field
                .setLabel("your reply...")
                .build()
            val replyIntent = Intent(this, NotificationReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(this, 1, replyIntent, 0)
            val replyAction = NotificationCompat.Action.Builder(R.drawable.ic_one, "Reply", replyPendingIntent)
                .addRemoteInput(remoteInput).build()

//        // handle message notification in lower apis
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            replyIntent = Intent(this, MainActivity::class.java)
//            replyPendingIntent = PendingIntent.getBroadcast(this, 1, replyIntent, 0)
//        }

            val self = Person.Builder()
                .setIcon(IconCompat.createWithResource(this, R.drawable.ic_user))
                .setImportant(true)
                .setName("Me")
                .build()
            val messageStyle = NotificationCompat.MessagingStyle(self)
            messageStyle.conversationTitle = "Group chat"

            MESSAGES.forEach { chat ->
                val message = NotificationCompat.MessagingStyle.Message(chat.text, chat.timeStamp, chat.sender)
                messageStyle.messages.add(message)
            }
            val notification = NotificationCompat.Builder(this, Constants.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messageStyle)
                .addAction(replyAction)
                .setColor(getColor(R.color.purple_500))
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.CYAN)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_one, "Mark as Read", null)
                .setOnlyAlertOnce(true)
                .build()
            sendNotification(1, notification)
        }
        binding.btnChannel2.setOnClickListener {
            val artwork = BitmapFactory.decodeResource(resources, R.drawable.image)

            val notification = NotificationCompat.Builder(this, Constants.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(binding.etTitle.text)
                .setContentText(binding.etDesc.text)
                .setLargeIcon(artwork)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(R.drawable.ic_play, "Play/Pause", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .addAction(R.drawable.ic_backup, "Backup", null)
                .addAction(R.drawable.ic_bluetooth, "bluetooth", null)
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                        .setShowCancelButton(false)
                        .setMediaSession(mediaSession.sessionToken)
                ).setSubText("Subtext")
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