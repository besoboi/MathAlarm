package com.example.mathalarm.domain

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mathalarm.R
import com.example.mathalarm.presentation.MathActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val newIntent = Intent(context, MathActivity::class.java)
        if (intent != null) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            newIntent,
            PendingIntent.FLAG_MUTABLE
        )
        if (context != null) {

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_icon)
                .setContentTitle(CONTENT_TITLE)
                .setContentText(CONTENT_TEXT)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(1000L, 1000L, 1000L, 1000L, 1000L))
                .setContentIntent(pendingIntent)

            val notification = builder.build().apply {
                flags = Notification.FLAG_INSISTENT or Notification.FLAG_AUTO_CANCEL
            }


            val notificationManager = NotificationManagerCompat.from(context)

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(NOTIFICATION_ID, notification)
            }

        }
    }

    companion object {
        private const val CHANNEL_ID = "math_alarm_channel_ID"
        private const val CONTENT_TITLE = "Alarm"
        private const val CONTENT_TEXT = "Solve the example to turn off."
        private const val NOTIFICATION_ID = 909090

    }

}