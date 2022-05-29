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
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.andromedical3a.R
import java.util.*
import kotlin.math.log


private const val TAG = "PasoMedicacionNumeroTomasMensuales"

class AddMedicacionSeleccionarDiasTomasMensuales : Fragment() {

    // Interfaz para comunicarme con MainActivity y decirle que monte el siguiente fragmento
    interface Callbacks {
        fun addMedicacionTipoCantidadyTipoMedicacion()
    }

    private var callbacks: Callbacks? = null

    private lateinit var lunes: CheckBox
    private lateinit var martes: CheckBox
    private lateinit var miercoles: CheckBox
    private lateinit var jueves: CheckBox
    private lateinit var viernes: CheckBox
    private lateinit var sabado: CheckBox
    private lateinit var domingo: CheckBox
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

    private val sharedMedication : AddMedicacionViewModel by activityViewModels()

    private var checkAccumulator : Int = 0;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(
            R.layout.fragment_medicacion_seleccionar_dias_tomas_mensuales,
            container,
            false
        )

        lunes = rootView.findViewById(R.id.Monday) as CheckBox
        martes = rootView.findViewById(R.id.Tuesday) as CheckBox
        miercoles = rootView.findViewById(R.id.Wednesday) as CheckBox
        jueves = rootView.findViewById(R.id.Thursday) as CheckBox
        viernes = rootView.findViewById(R.id.Friday) as CheckBox
        sabado = rootView.findViewById(R.id.Saturday) as CheckBox
        domingo = rootView.findViewById(R.id.Sunday) as CheckBox
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button


        return rootView
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        val selectedTime = Calendar.getInstance()

        atrasButton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento PasoMedicacionNumeroTomasMensuales")
            activity?.onBackPressed()
        }

        lunes.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(1.toString())
            checkAccumulator++
        }

        martes.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(2.toString())
            checkAccumulator++
        }

        miercoles.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(3.toString())
            checkAccumulator++
        }
        jueves.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(4.toString())
            checkAccumulator++
        }
        viernes.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(5.toString())
            checkAccumulator++
        }

        sabado.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(6.toString())
            checkAccumulator++
        }
        domingo.setOnClickListener {
            sharedMedication.setDiasTomasMensuales(7.toString())
            checkAccumulator++
        }

        continuarButton.setOnClickListener {
            if (sharedMedication.ValueTomasMensuales.value != checkAccumulator) {
                val dlgAlert = AlertDialog.Builder(this.context)
                dlgAlert.setTitle("MENSAJE DE AYUDA")
                dlgAlert.setMessage("Debe de seleccionar " + sharedMedication.ValueTomasMensuales.value + " dias de la semana. Usted ha seleccionado " + checkAccumulator + "\n\n ")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                sharedMedication.ValueDiasMedicacionMensual.value?.clear()
                checkAccumulator = 0
                lunes.isChecked = false
                martes.isChecked = false
                miercoles.isChecked = false
                jueves.isChecked = false
                viernes.isChecked = false
                sabado.isChecked = false
                domingo.isChecked = false
            } else {
                callbacks?.addMedicacionTipoCantidadyTipoMedicacion();
                checkAccumulator = 0
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