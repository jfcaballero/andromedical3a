package com.app.andromedical3a.administrationModulo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.app.andromedical3a.R

private const val TAG = "Administration"

class AdministrationFragment : Fragment() {

    interface Callbacks {
        fun administrationMedicineModulo()
        fun administrationCitaMedicoModulo()
    }

    private lateinit var backbutton: ImageButton
    private lateinit var helpbutton: ImageButton
    private lateinit var citeDoctorButton: Button
    private lateinit var medicineButton: Button

    private var callbacks: Callbacks? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "Fragmento $TAG creado")


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_administration, container, false)

        citeDoctorButton = view.findViewById(R.id.cita_medico_administration) as Button
        medicineButton = view.findViewById(R.id.pastillas_administration) as Button
        backbutton = view.findViewById(R.id.back_button_administration) as ImageButton
        helpbutton = view.findViewById(R.id.help_icon_administration) as ImageButton


        return view
    }

    override fun onStart() {
        super.onStart()

        backbutton.setOnClickListener {
            Log.i(
                com.app.andromedical3a.administrationModulo.TAG,
                "Saliendo de fragmento ${com.app.andromedical3a.administrationModulo.TAG}"
            )
            activity?.onBackPressed()
        }

        helpbutton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("MODULO ADMINISTRACION")
            dlgAlert.setMessage(
                "Pulse el boton de CITA MEDICO, si desea añadir o modificar las citas medicas del usuario.\n\n " +
                        "Pulse el boton de PASTILLAS, si desea añadir o modificar los tratamientos del usuario. \n\n"
            )
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        medicineButton.setOnClickListener {
            callbacks?.administrationMedicineModulo()
        }

        citeDoctorButton.setOnClickListener {
            callbacks?.administrationCitaMedicoModulo()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento $TAG")
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}

