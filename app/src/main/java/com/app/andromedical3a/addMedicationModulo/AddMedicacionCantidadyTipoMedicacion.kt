package com.app.andromedical3a.addMedicationModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
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

private const val TAG = "PasoMedicacionCantidadyTipoMedicacion"

class AddMedicacionCantidadyTipoMedicacion: Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun addFechaInicioyFechaFinal()
    }

    private var callbacks: Callbacks? = null

    private lateinit var addCantidad: EditText
    private lateinit var addTipoMedicamento: EditText
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    private val sharedMedication: AddMedicacionViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_medicacion_cantidad_tomas, container, false)

        addCantidad = rootView.findViewById(R.id.addCantidadTomas) as EditText
        addTipoMedicamento = rootView.findViewById(R.id.addTipoMedicamento) as EditText
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

        continuarButton.setOnClickListener {

            if (addCantidad.length() <= 0) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir al menos una cantidad a tomar del medicamento.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            if (addTipoMedicamento.length()<=0)
            {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir al menos una descripción del tipo de medicamento a tomar.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            if (addTipoMedicamento.length()>0 && addCantidad.length()>0) {
                sharedMedication.setCantidadToma(addCantidad.text.toString().toFloat())
                sharedMedication.setComentarioTomas(addTipoMedicamento.text.toString())
                callbacks?.addFechaInicioyFechaFinal();
            }

        }

    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento PasoMedicacionCantidadyTipoMedicacion")
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