package com.app.andromedical3a.citaMedicoModulo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import java.io.Serializable
import java.text.SimpleDateFormat


private const val TAG = "DetailCitaMedicoFragment"
private const val ARG_CITAMEDICACION = "citamedico"

class DetailCitaMedicoFragment : Fragment() {

    interface Callbacks

    private lateinit var nombreMedico: TextView
    private lateinit var comentarioCitaMedica: TextView
    private lateinit var fechaCita: TextView
    private lateinit var horaAlarma : TextView

    var citaMedicoPasada : CitaMedico? = null

    private var callbacks: Callbacks? = null

    private val detailCitaMedicaViewModel: DetailCitaMedicoViewModel by lazy {
        ViewModelProvider(this).get(DetailCitaMedicoViewModel::class.java)
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
        val view = inflater.inflate(R.layout.fragment_detalle_citamedico, container, false)


        nombreMedico = view.findViewById(R.id.nombreMedicoDetalle) as TextView
        comentarioCitaMedica = view.findViewById(R.id.comentario) as TextView
        fechaCita = view.findViewById(R.id.fechaCitaMedico) as TextView
        horaAlarma = view.findViewById(R.id.horaAlarma) as TextView

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "LongLogTag")
    override fun onStart() {
        super.onStart()

        val spfhours = SimpleDateFormat("HH:mm")
        val spf = SimpleDateFormat("dd/MMM/yyyy HH:mm")

        nombreMedico.text = detailCitaMedicaViewModel.citaMedico.nombreMedico
        fechaCita.text = spf.format(detailCitaMedicaViewModel.citaMedico.fecha_cita).toString()
        horaAlarma.text = spfhours.format(detailCitaMedicaViewModel.citaMedico.hora_alarma).toString()
        comentarioCitaMedica.text = detailCitaMedicaViewModel.citaMedico.informacion_cita

    }


    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
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
        fun newInstance(citaMedico: CitaMedico): DetailCitaMedicoFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CITAMEDICACION, citaMedico as Serializable)
            }
            return DetailCitaMedicoFragment()
                    .apply {
                        arguments = args
                    }
        }
    }

}
