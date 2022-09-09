package com.app.andromedical3a.administrationModulo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.app.andromedical3a.R

private const val TAG = "LoginAdministration"

class LoginAdministrationFragment : Fragment() {


    interface Callbacks {
        fun administrationModulo()
    }

    private var callbacks: Callbacks? = null

    private lateinit var insertpassword : EditText
    private lateinit var loginbutton : Button
    private lateinit var forgotpasswordbutton : Button
    private lateinit var helpbutton : ImageButton

    // Me creo el viewmodel para comprobar password.
    lateinit var loginadministrationviewmodel : LoginAdministrationViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login_configuration, container, false)

        insertpassword = view.findViewById(R.id.TextPassword) as EditText
        loginbutton = view.findViewById(R.id.login_button) as Button
        forgotpasswordbutton = view.findViewById(R.id.forget_password_button) as Button
        helpbutton = view.findViewById(R.id.help_icon) as ImageButton


        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        )


        Log.i(TAG, "Fragmento $TAG creado")

    }


    override fun onStart() {
        super.onStart()
        loginadministrationviewmodel = LoginAdministrationViewModel()

        helpbutton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("LOGIN MODULO ADMINISTRACION")
            dlgAlert.setMessage("Introduzca la contraseña de administrador y pulse el boton de LOGIN para acceder al modulo de administracion.\n\n" +
                    "Si ha olvidado la contraseña de administrador, pulse el boton de ¿HAS OLVIDADO LA CONTRASEÑA?. ")
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        forgotpasswordbutton.setOnClickListener{
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setMessage("¿HAS OLVIDADO LA CONTRASEÑA?. \n\n "+ "Su contraseña es: " + loginadministrationviewmodel.password)
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

        loginbutton.setOnClickListener {
            if  (loginadministrationviewmodel.checkpassword(insertpassword.text.toString()))
                callbacks?.administrationModulo()
            else
            {
                val dlgAlert = AlertDialog.Builder(this.context)

                dlgAlert.setMessage("Error en la contraseña introducida")
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
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

