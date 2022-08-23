package com.app.andromedical3a.addMedicationModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.andromedical3a.R
import java.util.*

private const val TAG = "PasoMedicacionNumeroTomasMensuales"

class AddMedicacionNumeroTomasMensuales : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun addMedicacionSeleccionarDiasTomasMensuales()
    }

    private var callbacks: Callbacks? = null

    private lateinit var unDiaSemana: RadioButton
    private lateinit var dosDiasSemana: RadioButton
    private lateinit var tresDiasSemana: RadioButton
    private lateinit var cincoDiasSemana: RadioButton
    private lateinit var cadaQuinceDias: RadioButton
    private lateinit var unDiaMes: RadioButton
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    private val sharedMedication : AddMedicacionViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_medicacion_numero_tomas_mensuales,container,false)

        unDiaSemana = rootView.findViewById(R.id.FirstButton) as RadioButton
        dosDiasSemana = rootView.findViewById(R.id.SecondButton) as RadioButton
        tresDiasSemana = rootView.findViewById(R.id.ThirdtButton) as RadioButton
        cincoDiasSemana = rootView.findViewById(R.id.FourButton) as RadioButton
        cadaQuinceDias = rootView.findViewById(R.id.FiveButton) as RadioButton
        unDiaMes = rootView.findViewById(R.id.SixButton) as RadioButton
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button


        return rootView
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoMedicacionNumeroTomasMensuales")
            activity?.onBackPressed()
        }

        unDiaSemana.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(
                this.context,
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                    sharedMedication.setTomasMensuales(1)
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
            )
            timePicker.setTitle("Seleccione la hora de la toma")
            timePicker.show()
        }

        dosDiasSemana.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(
                this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                    sharedMedication.setTomasMensuales(2)
                },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
            )
            timePicker.setTitle("Seleccione la  hora de la toma")
            timePicker.show()
        }

        tresDiasSemana.setOnClickListener{

                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                    sharedMedication.setTomasMensuales(3)
                },
                    now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la hora de la toma")
                timePicker.show()

        }
        cincoDiasSemana.setOnClickListener{

                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                    sharedMedication.setTomasMensuales(5)
                },
                    now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la hora de la toma")
                timePicker.show()

        }
        cadaQuinceDias.setOnClickListener{

                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                    sharedMedication.setTomasMensuales(15)
                },
                    now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la hora de la toma")
                timePicker.show()

        }

        unDiaMes.setOnClickListener{

            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                sharedMedication.setTomasMensuales(30)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.setTitle("Seleccione la hora de la toma")
            timePicker.show()

        }

        continuarButton.setOnClickListener {
            if (!unDiaSemana.isChecked && !dosDiasSemana.isChecked && !tresDiasSemana.isChecked && !cincoDiasSemana.isChecked && !cadaQuinceDias.isChecked && !unDiaMes.isChecked) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe de seleccionar al menos una de las opciones del numero de tomas del medicamento mensuales.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            } else
                callbacks?.addMedicacionSeleccionarDiasTomasMensuales();
        }
    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento PasoMedicacionNumeroTomasMensuales")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


}