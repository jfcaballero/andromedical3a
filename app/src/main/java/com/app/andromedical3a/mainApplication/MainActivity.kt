package com.app.andromedical3a.mainApplication

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.andromedical3a.R
import com.app.andromedical3a.addCitaMedicoModulo.*
import com.app.andromedical3a.addMedicationModulo.*
import com.app.andromedical3a.administrationAlarmMedicine.MyBroadcastReceiver
import com.app.andromedical3a.administrationModulo.*
import com.app.andromedical3a.calendarModulo.CalendarFragment
import com.app.andromedical3a.citaMedicoModulo.CitaMedicoFragment
import com.app.andromedical3a.citaMedicoModulo.DetailCitaMedicoFragment
import com.app.andromedical3a.medicacionModulo.DetailMedicineFragment
import com.app.andromedical3a.medicacionModulo.MedicineFragment

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), MainPageFragment.Callbacks, LoginAdministrationFragment.Callbacks, AdministrationFragment.Callbacks,
    AdministrationMedicineFragment.Callbacks, AddMedicacionDiariooNoModulo.Callbacks, AddMedicacionNumeroTomas.Callbacks, AddMedicacionNumeroTomasMensuales.Callbacks, AddMedicacionSeleccionarDiasTomasMensuales.Callbacks,
    AddMedicacionCantidadyTipoMedicacion.Callbacks, AddFechaInicioyFechaFinal.Callbacks, AddMedicacionNombre.Callbacks,
    MedicineFragment.Callbacks, DetailMedicineFragment.Callbacks, DetailMedicineFragmentAdmin.Callbacks,
    AdministrationCitaMedicoFragment.Callbacks, CitaMedicoFragment.Callbacks, AddCitaMedicoFechayAlarma.Callbacks, AddCitaMedicoNombreyComentario.Callbacks, DetailCitaMedicoFragment.Callbacks, DetailCitaMedicoFragmentAdmin.Callbacks{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentoActual = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragmentoActual == null) {
            val fragmento = MainPageFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragmento)
                    .commit()
        }

    }

    override fun deleteAlarmsCitaMedico(citaMedico: CitaMedico) {
            val id = citaMedico.id_alarma
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val myIntent = Intent(this, MyBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this, id.toInt(), myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager!!.cancel(pendingIntent)
    }

    override fun administrationModulo() {
        Log.i(TAG, "Montando modulo Administracion")
        val fragmentoAdministracion =
                AdministrationFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoAdministracion)
                .addToBackStack(null)
                .commit()
    }

    override fun loginadministrationModulo() {
        Log.i(TAG, "Montando modulo loginAdministracion")
        val fragmentologinAdministracion =
            LoginAdministrationFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragmentologinAdministracion)
                .addToBackStack(null)
                .commit()
    }
    override fun calendarModulo() {
        Log.i(TAG, "Montando modulo calendar")
         val fragmentocalendar =
                 CalendarFragment()
         supportFragmentManager
                 .beginTransaction()
                 .replace(R.id.fragment_container, fragmentocalendar)
                 .addToBackStack(null)
                 .commit()

    }

    override fun dateMedicModulo() {
        Log.i(TAG, "Montando modulo citeMedic")
         val fragmentocitaMedico =
                 CitaMedicoFragment()
         supportFragmentManager
                 .beginTransaction()
                 .replace(R.id.fragment_container, fragmentocitaMedico)
                 .addToBackStack(null)
                 .commit()

    }

    override fun medicineModulo() {
        Log.i(TAG, "Montando modulo MedicineModulo")
        val fragmentoMedicine =
                MedicineFragment()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoMedicine)
                .addToBackStack(null)
                .commit()
    }

    override fun administrationMedicineModulo() {
        Log.i(TAG, "Montando modulo AdministracionMedicine")
        val fragmentoAdministracionMedicine =
            AdministrationMedicineFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAdministracionMedicine)
            .addToBackStack(null)
            .commit()
    }

    override fun administrationCitaMedicoModulo() {
        Log.i(TAG, "Montando modulo AdministrationCitaMedicoFragment")
        val fragmentoAdministracionCitamedico =
            AdministrationCitaMedicoFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAdministracionCitamedico)
            .addToBackStack(null)
            .commit()
    }

    override fun addMedicacionNumeroTomas() {
        Log.i(TAG, "Montando modulo addMedicacionNumeroTomas")
        val fragmentoAddMedicacionNumeroTomas =
                AddMedicacionNumeroTomas()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoAddMedicacionNumeroTomas)
                .addToBackStack(null)
                .commit()
    }

    override fun addMedicacionNumeroTomasMensuales() {
        Log.i(TAG, "Montando modulo addMedicacionNumeroTomasMensuales")
        val fragmentoAddMedicacionNumeroTomasMensuales =
            AddMedicacionNumeroTomasMensuales()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddMedicacionNumeroTomasMensuales)
            .addToBackStack(null)
            .commit()
    }

    override fun addMedicacionDiariooNoModulo() {
        Log.i(TAG, "Montando modulo addMedicacionModulo")
        val fragmentoAddMedicacionDiariaoNo =
                AddMedicacionDiariooNoModulo()
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragmentoAddMedicacionDiariaoNo)
                .addToBackStack(null)
                .commit()
    }

    override fun detailMedicacionSeleccionadaAdmin(medicacion: Medicacion) {
        val fragmentodetailMedicacionSeleccionada =
            DetailMedicineFragmentAdmin.newInstance(medicacion)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentodetailMedicacionSeleccionada)
            .addToBackStack(null)
            .commit()
    }

    override fun detailMedicacionSeleccionada(medicacion: Medicacion) {
        Log.i(TAG, "Medicacion : ${medicacion.name}")
        val fragmentodetailMedicacionSeleccionada =
            DetailMedicineFragment.newInstance(medicacion)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentodetailMedicacionSeleccionada)
            .addToBackStack(null)
            .commit()
    }

    override fun addMedicacionTipoCantidadyTipoMedicacion() {
        Log.i(TAG, "Montando modulo addMedicacionCantidadyTipoMedicacion")
        val fragmentoAddMedicacionCantidadyTipoTomas =
            AddMedicacionCantidadyTipoMedicacion()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddMedicacionCantidadyTipoTomas)
            .addToBackStack(null)
            .commit()
    }

    override fun addFechaInicioyFechaFinal() {
        Log.i(TAG, "Montando modulo addFechaInicioyFechaFinal")
        val fragmentoAddFechaInicioyFechaFinal =
            AddFechaInicioyFechaFinal()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddFechaInicioyFechaFinal)
            .addToBackStack(null)
            .commit()
    }

    override fun addMedicacionNombre() {
        Log.i(TAG, "Montando modulo addMedicacionNombre")
            val fragmentoAddMedicacionNombre =
            AddMedicacionNombre()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddMedicacionNombre)
            .addToBackStack(null)
            .commit()
    }

    override fun addMedicacionSeleccionarDiasTomasMensuales() {
        Log.i(TAG, "Montando modulo addMedicacionSeleccionarDiasTomasMensuales")
        val fragmentoAddMedicacionSeleccionarDiasTomasMensuales =
            AddMedicacionSeleccionarDiasTomasMensuales()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddMedicacionSeleccionarDiasTomasMensuales)
            .addToBackStack(null)
            .commit()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun deleteAllAlarms(medicacion: Medicacion) {
        val ids = medicacion.id_alarmas

        ids.forEach {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val myIntent = Intent(this, MyBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                    this, it.toInt(), myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager!!.cancel(pendingIntent)
        }
    }

    override fun addCitaMedico() {
        Log.i(TAG, "Montando modulo addCitaMedico")
        val fragmentoAddCitaMedico =
            AddCitaMedicoFechayAlarma()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddCitaMedico)
            .addToBackStack(null)
            .commit()
    }

    override fun detailCitaMedicoSeleccionadaAdmin(citaMedico: CitaMedico) {
        val fragmentodetailCitaMedicoSeleccionadaAdmin =
            DetailCitaMedicoFragmentAdmin.newInstance(citaMedico)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentodetailCitaMedicoSeleccionadaAdmin)
            .addToBackStack(null)
            .commit()
    }

    override fun addCitaMedicoNombreyComentario() {
        Log.i(TAG, "Montando modulo addCitaMedicoNombreyComentario")
        val fragmentoAddCitaMedicoNombreyComentario =
            AddCitaMedicoNombreyComentario()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentoAddCitaMedicoNombreyComentario)
            .addToBackStack(null)
            .commit()
    }

    override fun detailCitaMedicoSeleccionada(citaMedico: CitaMedico) {
        val fragmentodetailCitaMedicoSeleccionada =
            DetailCitaMedicoFragment.newInstance(citaMedico)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentodetailCitaMedicoSeleccionada)
            .addToBackStack(null)
            .commit()
    }


}


