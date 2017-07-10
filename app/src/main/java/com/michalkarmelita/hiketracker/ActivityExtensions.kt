package com.michalkarmelita.hiketracker

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.NotificationCompat

/**
 * Created by MK on 10/07/2017.
 */

fun Activity.showNotification(notificationId: Int) {
    val builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My Notification Title")
            .setContentText("Something interesting happened")
            .setOngoing(true)

    val targetIntent = Intent(this, PicturesActivity::class.java)
    val contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    builder.setContentIntent(contentIntent)
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(notificationId, builder.build())
}

fun Activity.cancelNotification(notificationId: Int) {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.cancel(notificationId)
}