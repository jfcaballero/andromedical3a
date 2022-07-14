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
        setContentView(R.layout.fragment_citamedico)
        nombreMedico = findViewById(R.id.NombreMedico)
        horaCita = findViewById(R.id.horaCita)
        detalleCitaMedica = findViewById(R.id.detalleCitaMedica)
        botonParar = findViewById(R.id.botonParar)

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val myIntent = Intent(applicationContext, MyBroadcastReceiverCitaMedica::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, intent.getIntExtra("requestCode", 0), myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val mensajeAMostrar = intent.getStringExtra("fechacita")
        horaCita.text = "Fecha: $mensajeAMostrar"
        nombreMedico.text = "Doctor: " + intent.getStringExtra("nombreMedico")
        detalleCitaMedica.text = intent.getStringExtra("comentarioCitaMedico")

        Log.i(TAG, "Entrando ShowAlarmaMedicine")

        botonParar.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento ShowAlarmaMedicine")
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