package com.app.andromedical3a.administrationAlarmCitaMedica

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.andromedical3a.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


private const val TAG = "ShowAlarmaCitaMedico"

class ShowAlarmaCitaMedica : AppCompatActivity() {

    private lateinit var nombreMedico: TextView
    private lateinit var detalleCitaMedica: TextView
    private lateinit var horaCita: TextView
    private lateinit var botonParar: Button

    private val showAlarmaCitaMedicaViewModel: ShowAlarmaCitaMedicaViewModel by lazy {
        ViewModelProvider(this).get(ShowAlarmaCitaMedicaViewModel::class.java)
    }

    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_alarm_citamedico)
        nombreMedico = findViewById(R.id.NombreMedico)
        horaCita = findViewById(R.id.horaCita)
        detalleCitaMedica = findViewById(R.id.detalleCitaMedica)
        botonParar = findViewById(R.id.botonParar)

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val myIntent = Intent(applicationContext, MyBroadcastReceiverCitaMedica::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, intent.getIntExtra("requestCode", 0), myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val spf = SimpleDateFormat("dd/MMM/yyyy HH:mm")
        val date = Date(intent.getStringExtra("fechacita"))
        horaCita.text = spf.format(date)
        nombreMedico.text = intent.getStringExtra("nombreMedico")
        detalleCitaMedica.text = intent.getStringExtra("comentarioCitaMedico")

        Log.i(TAG, "Entrando ShowAlarmaCitaMedico")

        botonParar.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento ShowAlarmaCitaMedico")
                alarmManager.cancel(pendingIntent)
                Log.i(TAG, "Alarma eliminada : " + intent.getIntExtra("requestCode", 0))
                finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        exitProcess(0)
    }
}