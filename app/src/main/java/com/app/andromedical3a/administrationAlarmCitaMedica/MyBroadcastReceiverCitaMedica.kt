package com.app.andromedical3a.administrationAlarmCitaMedica

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
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.andromedical3a.R
import java.util.*


private const val TAG = "MyBroadcastReceiverCitaMedica"

private const val CHANNEL_ID = "NotificationCitaMedico"
private const val notificationId = 2000


class MyBroadcastReceiverCitaMedica  : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver", "ServiceCast", "LongLogTag")
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
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(2000000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(2000000)
        }
            var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            }
            val ringtone = RingtoneManager.getRingtone(context, alarmUri)

            // play ringtone
            ringtone.play()

            intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            Log.i(TAG, "Estamos en BroadCastReceiverCitaMedica")

            val fullScreenIntent = Intent(context, ShowAlarmaCitaMedica::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                this.putExtra("nombreMedico", intent.getStringExtra("nombreMedico"))
                this.putExtra("fechacita", intent.getStringExtra("fechacita"))
                this.putExtra("comentarioCitaMedico", intent.getStringExtra("comentarioCitaMedico"))
                this.putExtra("horaAlarma", intent.getStringExtra("horaAlarma"))
                this.putExtra("requestCode", intent.getIntExtra("requestCode",0))

            }

                val fullScreenPendingIntent = PendingIntent.getActivity(context, intent.getIntExtra("requestCode",0),
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.doctor)
                .setContentTitle("Alarma de cita medica")
                .setContentText("Tiene una cita programada hoy.")
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
                    description = "Hora de la cita medica"
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