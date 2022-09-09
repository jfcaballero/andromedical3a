package com.app.andromedical3a.addCitaMedicoModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
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
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.time.hours

private const val TAG = "PasoCitaMedicoFechayAlarma"

class AddCitaMedicoFechayAlarma : Fragment() {

    interface Callbacks {
        fun addCitaMedicoNombreyComentario()
    }

    private var callbacks: Callbacks? = null

    private val citaMedico : AddCitaMedicoViewModel by activityViewModels()

    private lateinit var fechaCitaMedico: EditText
    private lateinit var horaAlarmaCitaMedico: EditText
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView : View = inflater.inflate(R.layout.fragment_citamedico_fecha_cita_alarma,container,false)

        fechaCitaMedico = rootView.findViewById(R.id.addFechaCitaMedico) as EditText
        horaAlarmaCitaMedico = rootView.findViewById(R.id.addHoraAlarmaCitaMedico) as EditText
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button

        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag", "SetTextI18n")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoCitaMedicoFechayHoraAlarma")
            activity?.onBackPressed()
        }

        fechaCitaMedico.setOnClickListener {
            val now = Calendar.getInstance()
            val selectedDateAndHours = Calendar.getInstance()
            var date: String?
            val datePicker = DatePickerDialog(
                this.requireContext(), { _, year, month, day_of_month ->
                    selectedDateAndHours.set(Calendar.YEAR, year)
                    selectedDateAndHours.set(Calendar.MONTH, month)
                    selectedDateAndHours.set(Calendar.DAY_OF_MONTH, day_of_month)
                    date = day_of_month.toString() + " / " + (month+1) + " / " + year
                    fechaCitaMedico.setText(date + "  " + fechaCitaMedico.text.toString())
                    citaMedico.setFechaCita(selectedDateAndHours.time)

                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH) )
                datePicker.setTitle("Seleccione la fecha de la cita médica")
                datePicker.show()
                val timePicker = TimePickerDialog(
                    this.requireContext(), { _, hourOfDay, minute ->
                        selectedDateAndHours.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateAndHours.set(Calendar.MINUTE, minute)
                        date = selectedDateAndHours.time.hours.toString() + ":" + selectedDateAndHours.time.minutes.toString()
                        fechaCitaMedico.setText(date)
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    false
                )
                timePicker.setTitle("Seleccione la hora de la cita médica")
                timePicker.show()

        }

        horaAlarmaCitaMedico.setOnClickListener{
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(
                this.context, { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    citaMedico.setFechaAlarma(selectedTime.time)
                    horaAlarmaCitaMedico.setText(selectedTime.time.hours.toString() + ":" + selectedTime.time.minutes.toString())
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
            )
            timePicker.setTitle("Seleccione la hora de la toma")
            timePicker.show()

        }

        continuarButton.setOnClickListener {

            if (horaAlarmaCitaMedico.text.toString() == "")
            {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe de indicar una hora de alarma.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            if (fechaCitaMedico.text.toString() == "")
            {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe de indicar una fecha y hora de la cita médica.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            if ((horaAlarmaCitaMedico.text.toString() != "") && (fechaCitaMedico.text.toString() != ""))
            {
                val horaCita = Time(
                    citaMedico.ValueFechaCita.value?.hours!!,
                    citaMedico.ValueFechaCita.value?.minutes!!, 0
                )
                val horaAlarma = Time(
                    citaMedico.ValueFechaAlarma.value?.hours!!,
                    citaMedico.ValueFechaAlarma.value?.minutes!!, 0
                )

                if (horaCita < horaAlarma)
                {
                    val dlgAlert = AlertDialog.Builder(this.context)
                    dlgAlert.setTitle("MENSAJE DE AYUDA")
                    dlgAlert.setMessage("La hora de la alarma no puede ser superior a la hora de cita del medico.\n\n ")
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                else
                    callbacks?.addCitaMedicoNombreyComentario()
            }
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

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento PasoCitaMedicoFechayHoraAlarma")
    }



}