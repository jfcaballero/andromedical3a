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

private const val TAG = "PasoMedicacionNombre"

class AddMedicacionNombre : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun administrationModulo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var nombreMedicamento: EditText
    private lateinit var atrasButton: Button
    private lateinit var grabarButton: Button

    private val sharedMedication : AddMedicacionViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_medicacion_grabar_medicine,container,false)

        nombreMedicamento = rootView.findViewById(R.id.addName) as EditText
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        grabarButton = rootView.findViewById(R.id.ContinueButton) as Button


        return rootView
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoMedicacionNumeroTomas")
            activity?.onBackPressed()
        }

        grabarButton.setOnClickListener {
            if (nombreMedicamento.length() <= 0) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe añadir el nombre del medicamento.\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
            else {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AVISO")
                dlgAlert.setMessage("Va a grabar una medicacion.\n\n ")
                dlgAlert.setPositiveButton("SI") { _, _ ->
                    sharedMedication.setNombreMedicacion(nombreMedicamento.text.toString())
                    if (!sharedMedication.ValueMedicacionDiaria.value!!)
                        sharedMedication.addMedicationToBBDDConTomasMensuales()
                    else
                        sharedMedication.addMedicacionABBDD()

                    callbacks?.administrationModulo()
                };
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

}