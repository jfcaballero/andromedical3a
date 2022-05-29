package com.app.andromedical3a.addMedicationModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.andromedical3a.R
import java.util.*


private const val TAG = "PasoFechaInicioyFechaFinal"

class AddFechaInicioyFechaFinal : Fragment() {

    interface Callbacks {
        fun addMedicacionNombre()
    }

    private var callbacks: Callbacks? = null

    private lateinit var fechaInicio: EditText
    private lateinit var fechaFinal: EditText
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    private val sharedMedication: AddMedicacionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater.inflate(
            R.layout.fragment_medicacion_fecha_inicio_final,
            container,
            false
        )

        fechaInicio = rootView.findViewById(R.id.addFirstDate) as EditText
        fechaFinal = rootView.findViewById(R.id.addFinalDate) as EditText
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button

        return rootView
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoFechaInicioyFechaFinal")
            activity?.onBackPressed()
        }

        fechaInicio.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this.requireContext(), { _, year, month, day_of_month ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, day_of_month)
                    val date = day_of_month.toString() + " / " + (month+1) + " / " + year
                    fechaInicio.setText(date)
                    sharedMedication.setFechaInicio(selectedDate.time)
                    Log.i(TAG,"Inicio" + selectedDate.time)
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH) )
            datePicker.setTitle("Seleccione la fecha de inicio del tratamiento")
            datePicker.show()
        }

        fechaFinal.setOnClickListener{
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                    this.requireContext(), { _, year, month, day_of_month ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, day_of_month)
                    val date = day_of_month.toString() + " / " + (month+1) + " / " + year
                    fechaFinal.setText(date)
                    sharedMedication.setFechaFinal(selectedDate.time)
                    Log.i(TAG,"Final" + selectedDate.time)
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH) )
            datePicker.setTitle("Seleccione la fecha de final del tratamiento")
            datePicker.show()
        }

        continuarButton.setOnClickListener {

            if (fechaInicio.length()<=0) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir una fecha de inicio del tratamientos.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            if (fechaFinal.length()<=0)
            {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir una fecha de fin del tratamientos.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            if (fechaFinal.length()>0 && fechaInicio.length()>0 ) {
                callbacks?.addMedicacionNombre()
            }

        }

    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento PasoFechaInicioyFechaFinal")
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