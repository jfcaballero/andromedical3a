package com.app.andromedical3a.medicacionModulo

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
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationMedicineModulo.Medicacion
import com.app.andromedical3a.administrationModulo.AdministrationMedicineViewModel
import java.text.SimpleDateFormat


private const val TAG = "MedicineFragment"

class MedicineFragment : Fragment() {

    interface Callbacks {
        fun detailMedicacionSeleccionada(medicacion : Medicacion)
    }


    private lateinit var backbutton: ImageButton
    private lateinit var helpbutton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private var medicineListAdapter: MedicineListAdapter = MedicineListAdapter(emptyList())

    private var callbacks: Callbacks? = null

    private val medicineViewModel: AdministrationMedicineViewModel by lazy {
        ViewModelProvider(this).get(AdministrationMedicineViewModel::class.java)
    }

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
        val view = inflater.inflate(R.layout.fragment_medicine, container, false)


        recyclerView = view.findViewById(R.id.recyclervie_wmedicine) as RecyclerView
        recyclerView.adapter = medicineListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        backbutton = view.findViewById(R.id.back_button_medicine) as ImageButton
        helpbutton = view.findViewById(R.id.help_icon_medicine) as ImageButton

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        medicineViewModel.listaMedicacionLiveData.observe(
            viewLifecycleOwner
        ) { medicacion ->
            medicacion?.let {
                Log.d(TAG, "Numero de medicamentos : ${medicacion.size}")
                mostrarMedicacion(medicacion)
            }
        }

    }

    private fun mostrarMedicacion(medicacion: List<Medicacion>) {
        medicineListAdapter = MedicineListAdapter(medicacion)
        recyclerView.adapter = medicineListAdapter

    }

    override fun onStart() {
        super.onStart()

        backbutton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento $TAG")
            activity?.onBackPressed()
        }

        helpbutton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("VENTANA DE AYUDA")
            dlgAlert.setMessage("Esta en las MEDICACIONES, aquí se muestran una lista con todos las medicaciones asignadas. \n\n" +
                    "Si desea consultar una medicación en profuncidad, pulse sobre ella.")
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class MedicineListAdapter(var medicacion: List<Medicacion>) :
        RecyclerView.Adapter<MedicineListAdapter.MedicacionViewHolder>() {

        override fun getItemCount() = medicacion.size


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicacionViewHolder {
            val itemView: View = layoutInflater.inflate(R.layout.list_medicine, parent, false)

            return MedicacionViewHolder(itemView)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: MedicacionViewHolder, position: Int) {
            val medicacion: Medicacion = medicacion[position]
            holder.setData(medicacion, position)
            holder.bind(medicacion)
        }

        private inner class MedicacionViewHolder(view: View) :
            RecyclerView.ViewHolder(view),
            View.OnClickListener {

            private lateinit var medicacionMostrar : Medicacion

            private var mposition: Int = 0

            private var medicamento: TextView = itemView.findViewById(R.id.medicamento) as TextView
            private var imageMedicine: ImageView =
                itemView.findViewById(R.id.imageMedicine) as ImageView
            private var detallefechainicio: TextView =
                itemView.findViewById(R.id.detalleFechaInicio) as TextView
            private var detallefechafin: TextView =
                itemView.findViewById(R.id.detalleFechaFin) as TextView
            private var detallemonodosis: TextView =
                itemView.findViewById(R.id.detalleMonodosis) as TextView
            private var detalletomas: TextView =
                itemView.findViewById(R.id.detalleTomas) as TextView

            init {
                itemView.setOnClickListener(this)
            }

            fun bind (medicacion: Medicacion) {
                this.medicacionMostrar = medicacion
            }

            @SuppressLint("SimpleDateFormat", "SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.O)
            fun setData(medicacion: Medicacion, position: Int) {
                mposition = position
                //Add data of bbdd to display
                medicamento.text = medicacion.name
                imageMedicine.setImageBitmap(medicacion.foto_medicacion)
                val spf = SimpleDateFormat("dd/MMM/yyyy")
                detallefechainicio.text =  spf.format(medicacion.fecha_inicio)
                detallefechafin.text = spf.format(medicacion.fecha_fin)
                detallemonodosis.text = medicacion.numero_dosis.toString()
                if (medicacion.medicacion_diaria) {
                    if (medicacion.tomas_diarias.size == 1) {
                        detalletomas.text = "Dosís diarias: X "
                    }
                    if (medicacion.tomas_diarias.size == 2) {
                        detalletomas.text = "Dosís diarias: X - X "
                    }
                    if (medicacion.tomas_diarias.size == 3) {
                        detalletomas.text = "Dosís diarias: X - X - X "
                    }
                    if (medicacion.tomas_diarias.size == 4) {
                        detalletomas.text = "Dosís diarias: X - X - X - X "
                    }
                    if (medicacion.tomas_diarias.size == 5) {
                        detalletomas.text = "Dosís diarias: X - X - X - X - X "
                    }

                }
            }

            override fun onClick(view: View?) {
                val medicacionToShow: Medicacion = medicacionMostrar.copy()
                callbacks?.detailMedicacionSeleccionada(medicacionToShow)
            }


        }
    }
}
