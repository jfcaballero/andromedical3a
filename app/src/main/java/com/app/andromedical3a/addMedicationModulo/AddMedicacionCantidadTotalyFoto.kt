 package com.app.andromedical3a.addMedicationModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.app.andromedical3a.R

 private const val TAG = "PasoMedicacionCantidadyTotalyFoto"

 class AddMedicacionCantidadTotalyFoto : Fragment(){

    interface Callbacks {
        fun addFechaInicioyFechaFinal()
    }

     private var callbacks: Callbacks? = null

    private lateinit var addCantidadTotal: EditText
    private lateinit var addFotoMedicacion: ImageButton
    private lateinit var atrasButton: Button
    private lateinit var continuarButton: Button

     private val sharedMedication : AddMedicacionViewModel by activityViewModels()

     val REQUEST_IMAGE_CAPTURE = 1222
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_medicacion_cantidad_total_foto, container, false)

        addCantidadTotal = rootView.findViewById(R.id.addCantidadTomasTotal) as EditText
        addFotoMedicacion = rootView.findViewById(R.id.addFotoMedicamento) as ImageButton
        atrasButton = rootView.findViewById(R.id.ReturnButton) as Button
        continuarButton = rootView.findViewById(R.id.ContinueButton) as Button


        return rootView
    }

     @SuppressLint("LongLogTag")
     override fun onStart() {
         super.onStart()

         atrasButton.setOnClickListener {
             Log.i(TAG, "Saliendo de fragmento PasoMedicacionCantidadyTotalyFoto")
             activity?.onBackPressed()
         }

         addFotoMedicacion.setOnClickListener {
             addFotoMedicacion.isSelected = true
             val imageCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
             startActivityForResult(imageCamera,REQUEST_IMAGE_CAPTURE)
             //Log.i(TAG, "Pulsando boton tomar foto")
         }

         continuarButton.setOnClickListener {

             if (addCantidadTotal.length() <= 0) {
                 val dlgAlert = AlertDialog.Builder(this.context)
                 dlgAlert.setTitle("MENSAJE DE AYUDA")
                 dlgAlert.setMessage("Debe añadir la cantidad total del medicamento.\n\n ")
                 dlgAlert.setPositiveButton("OK", null);
                 dlgAlert.setCancelable(true);
                 dlgAlert.create().show();
             }

             if (!addFotoMedicacion.isSelected)
             {
                 val dlgAlert = AlertDialog.Builder(this.context)
                 dlgAlert.setTitle("MENSAJE DE AYUDA")
                 dlgAlert.setMessage("Debe añadir una foto del medicamento a tomar.\n\n ")
                 dlgAlert.setPositiveButton("OK", null);
                 dlgAlert.setCancelable(true);
                 dlgAlert.create().show();
             }

             if (addCantidadTotal.length()>0 && addFotoMedicacion.isSelected) {
                 sharedMedication.setCantidadTotal((addCantidadTotal.text).toString().toFloat())
                 callbacks?.addFechaInicioyFechaFinal()
             }

         }

     }


     @SuppressLint("LongLogTag")
     override fun onDestroy() {
         super.onDestroy()
         Log.i(TAG, "Destruyendo fragmento PasoMedicacionCantidadyTotalyFoto")
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == REQUEST_IMAGE_CAPTURE){
             /**save to Image In layout*/
             val images:Bitmap = data?.extras?.get("data") as Bitmap
             sharedMedication.setFotoMedicacion(images)
         }
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