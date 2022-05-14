package com.notificationsInAndroid

data class Message(val text: CharSequence, val sender: CharSequence?, val timeStamp: Long = System.currentTimeMillis())
