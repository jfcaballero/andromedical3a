package com.app.andromedical3a.mainApplication

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.app.andromedical3a.R

class MainPageFragment : Fragment() {

    // Interfaz para que MainActivity pueda hostear el fragmento y hacer callbacks
    interface Callbacks {
        fun loginadministrationModulo()
        fun calendarModulo()
        fun dateMedicModulo()
        fun medicineModulo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var helpButton: ImageButton
    private lateinit var configurationButton: ImageButton
    private lateinit var calendarButton: ImageButton
    private lateinit var citeDoctorButton: ImageButton
    private lateinit var medicineButton: ImageButton


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity_main, container, false)

        configurationButton = view.findViewById(R.id.setting_icon) as ImageButton
        calendarButton = view.findViewById(R.id.calendario) as ImageButton
        citeDoctorButton = view.findViewById(R.id.cita_medico) as ImageButton
        medicineButton = view.findViewById(R.id.pastillas) as ImageButton
        helpButton = view.findViewById(R.id.help_icon) as ImageButton


        return view
    }

    // Aqui pongo los listeners a los botones
    override fun onStart() {
        super.onStart()

        citeDoctorButton.setOnClickListener{
            callbacks?.dateMedicModulo()
        }

        calendarButton.setOnClickListener {
            callbacks?.calendarModulo()
        }

        configurationButton.setOnClickListener {
            callbacks?.loginadministrationModulo()
        }

        medicineButton.setOnClickListener {
            callbacks?.medicineModulo()
        }

            helpButton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("VENTANA DE AYUDA")
            dlgAlert.setMessage("Pulse el boton de CALENDARIO, si desea ver el calendario de sus medicaciones.\n\n " +
                    "Pulse el boton de CITA MÉDICO, si desea comprobar si tiene añadida alguna cita medica.\n\n " +
                    "Pulse el boton de MEDICACIÓN, si desea comprobar la lista de medicamentos que tiene asignados. \n\n")
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}