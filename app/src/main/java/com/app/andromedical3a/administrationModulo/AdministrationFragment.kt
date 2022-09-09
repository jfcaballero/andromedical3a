package com.app.andromedical3a.administrationModulo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.app.andromedical3a.R
import com.app.andromedical3a.mainApplication.MainActivity


private const val TAG = "Administration"

class AdministrationFragment : Fragment() {

    interface Callbacks {
        fun administrationMedicineModulo()
        fun administrationCitaMedicoModulo()
    }

    private lateinit var helpbutton: ImageButton
    private lateinit var citeDoctorButton: ImageButton
    private lateinit var medicineButton: ImageButton

    private var callbacks: Callbacks? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = false
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        )
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

        citeDoctorButton = view.findViewById(R.id.cita_medico) as ImageButton
        medicineButton = view.findViewById(R.id.pastillas) as ImageButton
        helpbutton = view.findViewById(R.id.help_icon_administration) as ImageButton


        return view
    }

    override fun onStart() {
        super.onStart()

        helpbutton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("MODULO ADMINISTRACION")
            dlgAlert.setMessage(
                "Pulse el boton de CITA MÉDICO, si desea añadir,eliminar o consultar las citas medicas del usuario.\n\n " +
                        "Pulse el boton de MEDICACIÓN, si desea añadir,eliminar o consultar los tratamientos del usuario. \n\n"
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

