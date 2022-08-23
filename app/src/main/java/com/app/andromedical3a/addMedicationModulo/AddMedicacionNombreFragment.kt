package com.app.andromedical3a.addMedicationModulo

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationMedicineModulo.Medicacion
import com.app.andromedical3a.administrationAlarmMedicine.MyBroadcastReceiver
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PasoMedicacionNombre"

@Suppress("DEPRECATION")
class AddMedicacionNombre : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun administrationModulo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var nombreMedicamento: EditText
    private lateinit var atrasButton: Button
    private lateinit var grabarButton: Button
    private lateinit var addFotoMedicacion: ImageButton
    private val REQUEST_IMAGE_CAPTURE = 1222

    var medicacion: Medicacion? = null

    private val sharedMedication: AddMedicacionViewModel by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_medicacion_grabar_medicine, container, false)


        nombreMedicamento = rootView.findViewById(R.id.addName) as EditText
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        grabarButton = rootView.findViewById(R.id.ContinueButton) as Button
        addFotoMedicacion = rootView.findViewById(R.id.addFotoMedicamento) as ImageButton


        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoMedicacionNumeroTomas")
            activity?.onBackPressed()
        }

        addFotoMedicacion.setOnClickListener {
            addFotoMedicacion.isSelected = true
            val imageCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(imageCamera,REQUEST_IMAGE_CAPTURE)
            //Log.i(com.app.andromedical3a.calendarModulo.TAG, "Pulsando boton tomar foto")
        }

        grabarButton.setOnClickListener {

            if (!addFotoMedicacion.isSelected)
            {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir una foto del medicamento a tomar.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            if (nombreMedicamento.length() <= 0) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir el nombre del medicamento.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            if (nombreMedicamento.length() > 0 && addFotoMedicacion.isSelected) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AVISO")
                dlgAlert.setMessage("Va a grabar una medicacion.\n\n ")
                dlgAlert.setPositiveButton("SI") { _, _ ->
                    sharedMedication.setNombreMedicacion(nombreMedicamento.text.toString())
                    medicacion = if (!sharedMedication.ValueMedicacionDiaria.value!!) {
                        sharedMedication.ValuehoraTomasTotales.value?.forEach { day ->
                            initAlarm(day)
                        }
                        sharedMedication.addMedicationToBBDDConTomasMensuales()
                    } else {
                        sharedMedication.ValuehoraTomasTotales.value?.forEach { day ->
                            initAlarmEachDay(day)
                        }
                        sharedMedication.addMedicacionABBDD()
                    }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE){
            /**save to Image In layout*/
            val images:Bitmap = data?.extras?.get("data") as Bitmap
            sharedMedication.setFotoMedicacion(images)
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initAlarmEachDay(day : String)
    {

        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        calendar.time = sdf.parse(day) // all done
        val hours = Date(sdf.parse(day).time)

        // Depending on the version of Android use different function for setting an
        // Alarm.
        // setAlarmClock() - used for everything lower than Android M
        // setExactAndAllowWhileIdle() - used for everything on Android M and higher
        calendar.time = sharedMedication.ValueFechaInicio.value
        while (calendar.time.before(sharedMedication.ValueFechaFinal.value)) {

            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MyBroadcastReceiver::class.java)

            val id = (0..1000000).random()
            sharedMedication.setidsAlarmass(id.toString())

            intent.putExtra("fotoMedicacion", sharedMedication.ValueFotoMedicacion.value)
            intent.putExtra("tomaDiaria", sharedMedication.ValueCantidadToma.value)
            intent.putExtra("comentarioToma", sharedMedication.ValueComentarioTomas.value)
            intent.putExtra("nombreMedicacion", sharedMedication.ValuenombreMedicacion.value)
            intent.putExtra("requestCode", id)

            // Used for filtering inside Broadcast receiver intent.action = "MyBroadcastReceiverAction"
            val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                calendar.set(Calendar.HOUR_OF_DAY, hours.hours)
                calendar.set(Calendar.MINUTE, hours.minutes)
                Log.i(TAG, "Días:" + calendar.time)
                alarmManager.setRepeating(RTC_WAKEUP,
                        calendar.timeInMillis,
                        5*60*1000,
                        pendingIntent)
            calendar.add(Calendar.DATE, 1)
        }
    }

    @SuppressLint("NewApi")
    private fun initAlarm(day: String)
    {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        calendar.time = sdf.parse(day) // all done

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MyBroadcastReceiver::class.java)

        val id = (0..1000000).random()
        sharedMedication.setidsAlarmass(id.toString())

        intent.putExtra("fotoMedicacion", sharedMedication.ValueFotoMedicacion.value)
        intent.putExtra("tomaDiaria", sharedMedication.ValueCantidadToma.value)
        intent.putExtra("comentarioToma", sharedMedication.ValueComentarioTomas.value)
        intent.putExtra("nombreMedicacion", sharedMedication.ValuenombreMedicacion.value)
        intent.putExtra("requestCode", id)
        intent.putExtra("horaMedicacion", calendar.time)

        // Used for filtering inside Broadcast receiver intent.action = "MyBroadcastReceiverAction"
        val pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)

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

