package com.app.andromedical3a.addMedicationModulo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.andromedical3a.R

private const val TAG = "PasoMedicacionDiariaoNo"

class AddMedicacionDiariooNoModulo : Fragment() {

    interface Callbacks {
        fun addMedicacionNumeroTomas()
        fun addMedicacionNumeroTomasMensuales()
    }

    private var callbacks: Callbacks? = null

    private val medicacionDiariaoNoViewModel : AddMedicacionViewModel by lazy {
        ViewModelProvider(this).get(AddMedicacionViewModel::class.java)
    }

    private lateinit var siButton: RadioButton
    private lateinit var noButton: RadioButton
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_medicacion_diaria_o_no,container,false)

        siButton = rootView.findViewById(R.id.FirstButton) as RadioButton
        noButton = rootView.findViewById(R.id.SecondButton) as RadioButton
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button

        return rootView
    }

    override fun onStart() {
        super.onStart()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoMedicacionDiariaoNo")
            activity?.onBackPressed()
        }

        continuarButton.setOnClickListener {

            when {
                noButton.isChecked -> {
                    callbacks?.addMedicacionNumeroTomasMensuales()
                    medicacionDiariaoNoViewModel.setMedicacionDiaria(false)
                }
                siButton.isChecked -> {
                    medicacionDiariaoNoViewModel.setMedicacionDiaria(true);
                    callbacks?.addMedicacionNumeroTomas()
                }
                else -> {
                    val dlgAlert = AlertDialog.Builder(this.context)
                    dlgAlert.setTitle("MENSAJE DE AYUDA")
                    dlgAlert.setMessage("Debe seleccionar si su medicacion es diaria o no.\n\n ")
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento PasoMedicacionDiariaoNo")
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