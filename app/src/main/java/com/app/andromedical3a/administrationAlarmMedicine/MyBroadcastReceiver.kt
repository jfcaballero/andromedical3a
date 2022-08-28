package com.app.andromedical3a.administrationAlarmMedicine

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.andromedical3a.R
import java.util.*


private const val TAG = "MyBroadcastReceiver"

private const val CHANNEL_ID = "NotificationMedicacion"
private const val notificationId = 1000


class MyBroadcastReceiver  : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver", "ServiceCast")
    override fun onReceive(context: Context?, intent: Intent?) {
        /*
        val vibrator = context!!.getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(4000)
            var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
            val ringtone = RingtoneManager.getRingtone(context, alarmUri)

            // play ringtone
            ringtone.play()
         */

            val vibrator = context!!.getSystemService(VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(2000000)
            var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
            val ringtone = RingtoneManager.getRingtone(context, alarmUri)

            // play ringtone
            ringtone.play()

            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Log.i(TAG, "Estamos en BroadCastReceiver")

            val fullScreenIntent = Intent(context, ShowAlarmaMedicacion::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                this.putExtra("fotoMedicacion", intent.getParcelableExtra("fotoMedicacion") as Bitmap)
                this.putExtra("tomaDiaria", intent.getFloatExtra("tomaDiaria",0.0f))
                this.putExtra("nombreMedicacion", intent.getStringExtra("nombreMedicacion"))
                this.putExtra("comentarioToma", intent.getStringExtra("comentarioToma"))
                this.putExtra("requestCode", intent.getIntExtra("requestCode",0))
                this.putExtra("horaMedicacion",intent.getStringExtra("horaMedicacion"))


            }

                Log.i(TAG, intent.getStringExtra("nombreMedicacion").toString())
                val fullScreenPendingIntent = PendingIntent.getActivity(context, intent.getIntExtra("requestCode",0),
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.pills)
                .setContentTitle("Hora de la medicación")
                .setContentText("Hora de tomarte " + intent.getStringExtra("nombreMedicacion"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                    .setTimeoutAfter(120000)

            Handler().postDelayed({
                ringtone.stop()
                vibrator.cancel()
            }, 120000)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, "name", importance).apply {
                    description = "Hora de la medicación"
                }
                // Register the channel with the system
                val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                notificationManager.createNotificationChannel(channel)
            }

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }



    }
}