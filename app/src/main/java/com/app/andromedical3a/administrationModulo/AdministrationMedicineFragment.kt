package com.app.andromedical3a.administrationModulo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.andromedical3a.R
import com.app.andromedical3a.administrationMedicineModulo.Medicacion
import java.util.*


private const val TAG = "AdministrationMedicine"

class AdministrationMedicineFragment : Fragment() {

    interface Callbacks {
        fun addMedicacionModulo()
    }


    private lateinit var backbutton: ImageButton
    private lateinit var helpbutton: ImageButton
    private lateinit var addmedication: ImageButton
    private lateinit var recyclerView: RecyclerView
    private var medicineListAdapter: MedicineListAdapter = MedicineListAdapter(emptyList())

    private lateinit var medicacion : Medicacion
    private lateinit var medicacion1 : Medicacion

    private var callbacks: Callbacks? = null

    private val administrationMedicineViewModel: AdministrationMedicineViewModel by lazy {
        ViewModelProvider(this).get(AdministrationMedicineViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        medicacion  = Medicacion(UUID.randomUUID(),"paracetamol",Date(),Date(),40,2f, Calendar.getInstance(),
            BitmapFactory.decodeResource(context?.resources,R.drawable.pastillasmain))

         medicacion1  = Medicacion(UUID.randomUUID(),"ibuprofenon",Date(),Date(),40,2f, Calendar.getInstance(),
            BitmapFactory.decodeResource(context?.resources,R.drawable.pills))

        administrationMedicineViewModel

        administrationMedicineViewModel.addMedicacion(medicacion)
        administrationMedicineViewModel.addMedicacion(medicacion1)
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
        val view = inflater.inflate(R.layout.fragment_administration_medicine, container, false)


        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        recyclerView.adapter = medicineListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        addmedication = view.findViewById(R.id.addMedication) as ImageButton
        backbutton = view.findViewById(R.id.back_button_administration_medicine) as ImageButton
        helpbutton = view.findViewById(R.id.help_icon_administration_medicine) as ImageButton

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        administrationMedicineViewModel.listaMedicacionLiveData.observe(
            viewLifecycleOwner, { medicacion ->
                medicacion?.let {
                    Log.d(TAG, "Numero de medicamentos : ${medicacion.size}")
                    mostrarMedicacion(medicacion)
                }
            }
        )


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

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento $TAG")
        administrationMedicineViewModel.deleteMedicacion(medicacion)
        administrationMedicineViewModel.deleteMedicacion(medicacion1)

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

        override fun onBindViewHolder(holder: MedicacionViewHolder, position: Int) {
            val medicacion: Medicacion = medicacion[position]
            holder.setData(medicacion, position)
        }

        private inner class MedicacionViewHolder(view : View) :
            RecyclerView.ViewHolder(view),
            View.OnClickListener {

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

            fun setData(medicacion: Medicacion, position: Int) {
                mposition = position
                //Add data of bbdd to display
                medicamento.text = medicacion.name
                imageMedicine.setImageBitmap(medicacion.foto_medicacion)
                detallefechainicio.text = medicacion.fecha_inicio.toString()
                detallefechafin.text = medicacion.fecha_fin.toString()
                detallemonodosis.text = medicacion.numero_dosis.toString()
                detalletomas.text = medicacion.tomas_diarias.toString()
            }

            override fun onClick(view: View?) {
                TODO("Not yet implemented")
            }


        }
    }
}
