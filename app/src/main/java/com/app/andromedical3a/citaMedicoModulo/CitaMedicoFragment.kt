package com.app.andromedical3a.citaMedicoModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.administrationModulo.AdministrationCitaMedicoViewModel
import java.text.SimpleDateFormat


private const val TAG = "CitaMedico"

class CitaMedicoFragment : Fragment() {

    interface Callbacks {
        fun detailCitaMedicoSeleccionada(citaMedico: CitaMedico)
    }

    private lateinit var helpbutton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private var citaMedicoListAdapter: CitaMedicoListAdapter = CitaMedicoListAdapter(emptyList())


    private var callbacks: Callbacks? = null

    private val citaMedicoViewModel: AdministrationCitaMedicoViewModel by lazy {
        ViewModelProvider(this).get(AdministrationCitaMedicoViewModel::class.java)
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

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_citamedico, container, false)


        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.adapter = citaMedicoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        helpbutton = view.findViewById(R.id.help_icon_administration_citaMedico) as ImageButton

        return view
    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        citaMedicoViewModel.listaCitaMedicoLiveData.observe(
            viewLifecycleOwner
        ) { citaMedico ->
            citaMedico?.let {
                Log.d(TAG, "Numero de citas de medico : ${citaMedico.size}")
                mostrarCitaMedico(citaMedico)
            }
        }

    }

    private fun mostrarCitaMedico(citaMedico: List<CitaMedico>) {
        citaMedicoListAdapter = CitaMedicoListAdapter(citaMedico)
        recyclerView.adapter = citaMedicoListAdapter

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()


        helpbutton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("VENTANA DE AYUDA")
            dlgAlert.setMessage("Esta en las CITAS MEDICAS, aquí se muestran una lista con todos las citas medicas asignadas. \n\n" +
            "Si desea consultar una cita en profuncidad, pulse sobre ella.")
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento")
    }

    private inner class CitaMedicoListAdapter(var citaMedico: List<CitaMedico>) :
        RecyclerView.Adapter<CitaMedicoListAdapter.CitaMedicoViewHolder>() {

        override fun getItemCount() = citaMedico.size


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaMedicoViewHolder {
            val itemView: View = layoutInflater.inflate(R.layout.list_citamedico, parent, false)

            return CitaMedicoViewHolder(itemView)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: CitaMedicoViewHolder, position: Int) {
            val citaMedico: CitaMedico = citaMedico[position]
            holder.setData(citaMedico, position)
            holder.bind(citaMedico)
        }

        private inner class CitaMedicoViewHolder(view: View) :
            RecyclerView.ViewHolder(view),
            View.OnClickListener {

            private lateinit var citaMedicoMostrar : CitaMedico

            private var mposition: Int = 0

            private var citaMedica: TextView = itemView.findViewById(R.id.nombreMedico) as TextView
            private var detalleFechaCita: TextView =
                itemView.findViewById(R.id.detalleFechaCita) as TextView
            private var detalleAlarmaCita: TextView =
                itemView.findViewById(R.id.detalleAlarmaCita) as TextView
            private var informacionCita: TextView =
                itemView.findViewById(R.id.informacionCita) as TextView

            init {
                itemView.setOnClickListener(this)
            }

            fun bind (citaMedico: CitaMedico) {
                this.citaMedicoMostrar = citaMedico
            }

            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            fun setData(citaMedico: CitaMedico, position: Int) {
                mposition = position
                //Add data of bbdd to display
                citaMedica.text = citaMedico.nombreMedico
                informacionCita.text = citaMedico.informacion_cita
                //imageMedicine.setImageBitmap(medicacion.foto_medicacion)
                val spf = SimpleDateFormat("dd/MMM/yyyy HH:mm")
                val spfHours = SimpleDateFormat("HH:mm")
                detalleFechaCita.text = spf.format(citaMedico.fecha_cita)
                detalleAlarmaCita.text = spfHours.format(citaMedico.hora_alarma)
            }

            override fun onClick(view: View?) {
                val citaMedicoToShow: CitaMedico = citaMedicoMostrar.copy()
                callbacks?.detailCitaMedicoSeleccionada(citaMedicoToShow)
            }


        }
    }
}
