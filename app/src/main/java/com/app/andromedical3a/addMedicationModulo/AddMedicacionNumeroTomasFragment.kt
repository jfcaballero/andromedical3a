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

private const val TAG = "PasoMedicacionNumeroTomas"

class AddMedicacionNumeroTomas : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun addMedicacionTipoCantidadyTipoMedicacion()
    }

    private var callbacks: Callbacks? = null

    private lateinit var unaTomaButton: RadioButton
    private lateinit var dosTomasButton: RadioButton
    private lateinit var tresTomasButton: RadioButton
    private lateinit var cuatroTomasButton: RadioButton
    private lateinit var seisTomasButton: RadioButton
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    private val sharedMedication : AddMedicacionViewModel by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_medicacion_numero_tomas,container,false)

        unaTomaButton = rootView.findViewById(R.id.FirstButton) as RadioButton
        dosTomasButton = rootView.findViewById(R.id.SecondButton) as RadioButton
        tresTomasButton = rootView.findViewById(R.id.ThirdtButton) as RadioButton
        cuatroTomasButton = rootView.findViewById(R.id.FourButton) as RadioButton
        seisTomasButton = rootView.findViewById(R.id.FiveButton) as RadioButton
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button


        return rootView
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoMedicacionNumeroTomas")
            activity?.onBackPressed()
        }

        unaTomaButton.setOnClickListener{
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
            }, now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.setTitle("Seleccione la 1 hora de la toma")
            timePicker.show()

        }
        dosTomasButton.setOnClickListener{
            var i =2
            while  (i >= 1)
            {
                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                },
                        now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la $i hora de la toma")
                timePicker.show()
                i--
            }

        }
        tresTomasButton.setOnClickListener{
            var i =3
            while (i >= 1)
            {
                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                },
                        now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la $i hora de la toma")
                timePicker.show()
                i--
            }
        }
        cuatroTomasButton.setOnClickListener{
            var i =4
            while (i >= 1)
            {
                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                },
                        now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la $i hora de la toma")
                timePicker.show()
                i--
            }

        }
        seisTomasButton.setOnClickListener{
            var i =6
            while (i >= 1)
            {
                val now = Calendar.getInstance()
                val timePicker = TimePickerDialog(this.context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    selectedTime.set(Calendar.MINUTE,minute)
                    sharedMedication.sethoraTomasTotales(selectedTime.time.toString())
                },
                        now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
                timePicker.setTitle("Seleccione la $i hora de la toma")
                timePicker.show()
                i--
            }

        }

        continuarButton.setOnClickListener {
            if (!unaTomaButton.isChecked && !dosTomasButton.isChecked && !tresTomasButton.isChecked && !cuatroTomasButton.isChecked && !seisTomasButton.isChecked) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe de seleccionar al menos una de las opciones del numero de tomas del medicamento.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            } else {
                callbacks?.addMedicacionTipoCantidadyTipoMedicacion();
            }
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