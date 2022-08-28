package com.app.andromedical3a.administrationAlarmMedicine

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


private const val TAG = "ShowAlarmaMedicine"

class ShowAlarmaMedicacion : AppCompatActivity() {


    private lateinit var imagenMedicacion: ImageView
    private lateinit var mensajeMedicacion: TextView
    private lateinit var medicacion: TextView
    private lateinit var botonTomar: Button

    private val showAlarmaMedicacionViewModel: ShowAlarmaMedicacionViewModel by lazy {
        ViewModelProvider(this).get(ShowAlarmaMedicacionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_alarm)
        medicacion = findViewById(R.id.title)
        imagenMedicacion = findViewById(R.id.imageMedicine)
        mensajeMedicacion = findViewById(R.id.medicamento)
        botonTomar = findViewById(R.id.botonTomar)

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val myIntent = Intent(applicationContext, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, intent.getIntExtra("requestCode", 0), myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        imagenMedicacion.setImageBitmap(intent.getParcelableExtra("fotoMedicacion"))
        val mensajeAMostrar = intent.getFloatExtra("tomaDiaria", 0.0f).toString() + " " + intent.getStringExtra("comentarioToma")
        mensajeMedicacion.text = mensajeAMostrar
        medicacion.text = intent.getStringExtra("nombreMedicacion")

        Log.i(TAG, "Entrando ShowAlarmaMedicine")

        botonTomar.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento ShowAlarmaMedicine")
            showAlarmaMedicacionViewModel.sumarMedicacionTotal(intent.getStringExtra("nombreMedicacion"), intent.getFloatExtra("tomaDiaria", 0.0f))
            //Modificar el valor de las tomasTomadas
            showAlarmaMedicacionViewModel.actualizarTomasRealizadas(intent.getStringExtra("nombreMedicacion"),intent.getStringExtra("horaMedicacion"))
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