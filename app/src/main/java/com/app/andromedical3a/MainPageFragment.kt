package com.app.andromedical3a

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class MainPageFragment : Fragment() {

    // Interfaz para que MainActivity pueda hostear el fragmento y hacer callbacks
    interface Callbacks {
        fun loginadministrationModulo()
        fun calendarModulo()
        fun dateMedicModulo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var helpButton: ImageButton
    private lateinit var configurationButton: ImageButton
    private lateinit var calendarButton: Button
    private lateinit var citeDoctorButton: Button
    private lateinit var medicineButton: Button


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity_main, container, false)

        configurationButton = view.findViewById(R.id.setting_icon) as ImageButton
        calendarButton = view.findViewById(R.id.calendario) as Button
        citeDoctorButton = view.findViewById(R.id.cita_medico) as Button
        medicineButton = view.findViewById(R.id.pastillas) as Button
        helpButton = view.findViewById(R.id.help_icon) as ImageButton


        return view
    }

    // Aqui pongo los listeners a los botones
    override fun onStart() {
        super.onStart()

        configurationButton.setOnClickListener {
            callbacks?.loginadministrationModulo()
        }

        helpButton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("VENTANA DE AYUDA")
            dlgAlert.setMessage("Pulse el boton de CALENDARIO, si desea ver el calendario de sus medicaciones.\n\n " +
                    "Pulse el boton de CITA MEDICO, si desea comprobar si tiene añadida alguna cita medica.\n\n " +
                    "Pulse el boton de PASTILLAS, si desea comprobar la lista de medicamentos que tiene asignados. \n\n")
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