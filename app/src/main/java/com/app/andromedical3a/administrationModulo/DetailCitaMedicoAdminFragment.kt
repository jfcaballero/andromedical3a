package com.app.andromedical3a.administrationModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.citaMedicoModulo.DetailCitaMedicoViewModel
import java.io.Serializable
import java.text.SimpleDateFormat

private const val TAG = "DetailCitaMedicoFragmentAdmin"
private const val ARG_CITAMEDICACION = "citamedico"

class DetailCitaMedicoFragmentAdmin : Fragment() {


    interface Callbacks {
        fun deleteAlarmsCitaMedico(citaMedico: CitaMedico)
        fun administrationModulo()
    }


    private lateinit var nombreMedico: TextView
    private lateinit var comentarioCitaMedica: TextView
    private lateinit var fechaCita: TextView
    private lateinit var horaAlarma : TextView
    private lateinit var deleteCitaMedico: Button
    var fecha : List<String> = emptyList()

    var citaMedicoPasada : CitaMedico? = null

    private var callbacks: Callbacks? = null

    private val detailCitaMedicaViewModel: DetailCitaMedicoViewModel by lazy {
        ViewModelProvider(this).get(DetailCitaMedicoViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    @SuppressLint("LongLogTag")
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
        citaMedicoPasada = arguments?.getSerializable(
            ARG_CITAMEDICACION
        ) as CitaMedico
        detailCitaMedicaViewModel.cargarCitaMedica(citaMedicoPasada!!)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_citamedico_admin, container, false)


        nombreMedico = view.findViewById(R.id.nombreMedicoDetalle) as TextView
        comentarioCitaMedica = view.findViewById(R.id.comentario) as TextView
        fechaCita = view.findViewById(R.id.fechaCitaMedico) as TextView
        horaAlarma = view.findViewById(R.id.horaAlarma) as TextView
        deleteCitaMedico = view.findViewById(R.id.deleteCitaMedico) as Button

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spfhours = SimpleDateFormat("HH:mm")
        val spf = SimpleDateFormat("dd/MMM/yyyy HH:mm")

        nombreMedico.text = detailCitaMedicaViewModel.citaMedico.nombreMedico
        fechaCita.text = spf.format(detailCitaMedicaViewModel.citaMedico.fecha_cita).toString()
        horaAlarma.text = spfhours.format(detailCitaMedicaViewModel.citaMedico.hora_alarma).toString()
        comentarioCitaMedica.text = detailCitaMedicaViewModel.citaMedico.informacion_cita

        deleteCitaMedico.setOnClickListener {
            //callbacks?.addMedicacionDiariooNoModulo()
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("MENSAJE DE AVISO")
            dlgAlert.setMessage("Va a eliminar la cita medica.\n\n ")
            dlgAlert.setPositiveButton("SI") { _, _ ->
                detailCitaMedicaViewModel.deleteCitaMedicoById(citaMedicoPasada?.id)
                callbacks?.deleteAlarmsCitaMedico(citaMedicoPasada!!)
                callbacks?.administrationModulo()
            }
            dlgAlert.setNegativeButton("NO", null);
            dlgAlert.create().show();
            activity?.onBackPressed()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "LongLogTag")
    override fun onStart() {
        super.onStart()

    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento")
    }

    companion object {
        /* Crea instancias de AjustesCitaFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, la cita de peluqueria pulsada
         */
        fun newInstance(citaMedico: CitaMedico): DetailCitaMedicoFragmentAdmin {
            val args = Bundle().apply {
                putSerializable(ARG_CITAMEDICACION, citaMedico as Serializable)
            }
            return DetailCitaMedicoFragmentAdmin()
                    .apply {
                        arguments = args
                    }
        }
    }


}