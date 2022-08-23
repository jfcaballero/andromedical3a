package com.app.andromedical3a.addCitaMedicoModulo

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationAlarmCitaMedica.MyBroadcastReceiverCitaMedica
import com.app.andromedical3a.administrationAlarmMedicine.MyBroadcastReceiver
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PasoCitaMedicoNombreyComentario"

class AddCitaMedicoNombreyComentario : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun administrationModulo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var nombreMedico: EditText
    private lateinit var atrasButton: Button
    private lateinit var grabarButton: Button
    private lateinit var comentarioCitaMedico: EditText

    private val citaMedico : AddCitaMedicoViewModel by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_citamedico_grabar_cita, container, false)


        nombreMedico = rootView.findViewById(R.id.addName) as EditText
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        grabarButton = rootView.findViewById(R.id.ContinueButton) as Button
        comentarioCitaMedico = rootView.findViewById(R.id.addComentario) as EditText


        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoCitaMedicoNombreyComentario")
            activity?.onBackPressed()
        }

        grabarButton.setOnClickListener {

            if (comentarioCitaMedico.length() <= 0)
            {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir una comentario a la cita médica.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            if (nombreMedico.length() <= 0) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir el nombre del medicamento.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            if (nombreMedico.length() > 0 && comentarioCitaMedico.length() > 0) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AVISO")
                dlgAlert.setMessage("Va a grabar una cita médica.\n\n ")
                dlgAlert.setPositiveButton("SI") { _, _ ->
                    citaMedico.setNombreMedico(nombreMedico.text.toString())
                    citaMedico.setComentariosCitaMedico(comentarioCitaMedico.text.toString())
                    initAlarm(citaMedico.ValueFechaCita.value,citaMedico.ValueFechaAlarma.value)
                    citaMedico.addCitaMedicoBBDD()
                callbacks?.administrationModulo()
                }
                dlgAlert.setNegativeButton("NO", null);
                dlgAlert.create().show();

            }
        }
    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento MedicacionNombre")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    @SuppressLint("NewApi", "LongLogTag")
    private fun initAlarm(diaCitaMedico: Date?, horaAlarma: Date?)
    {
        val calendar = Calendar.getInstance()
        diaCitaMedico?.hours = horaAlarma?.hours!!
        diaCitaMedico?.minutes = horaAlarma.minutes
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        calendar.time = sdf.parse(diaCitaMedico.toString()) // all done

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyBroadcastReceiverCitaMedica::class.java)

        val id = (0..1000000).random()
        citaMedico.setIdAlarma(id.toString())

        intent.putExtra("nombreMedico", citaMedico.ValueNombreMedico.value)
        intent.putExtra("fechacita", citaMedico.ValueFechaCita.value.toString())
        intent.putExtra("comentarioCitaMedico", citaMedico.ValueComentariosCitaMedico.value)
        intent.putExtra("horaAlarma", citaMedico.ValueFechaAlarma.value.toString())
        intent.putExtra("requestCode", id)

        // Used for filtering inside Broadcast receiver intent.action = "MyBroadcastReceiverAction"
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.i(TAG, "Día:" + calendar.time)
        // Depending on the version of Android use different function for setting an
        // Alarm.
        // setAlarmClock() - used for everything lower than Android M
        // setExactAndAllowWhileIdle() - used for everything on Android M and higher
        alarmManager.setRepeating(RTC_WAKEUP,
            calendar.timeInMillis,
            5*60*1000,
            pendingIntent)
    }

}

