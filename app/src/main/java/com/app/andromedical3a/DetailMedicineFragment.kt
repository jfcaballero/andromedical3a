package com.app.andromedical3a

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
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.andromedical3a.addMedicationModulo.Medicacion
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "DetailMedicineFragment"
private const val ARG_MEDICACION = "medicacion"

class DetailMedicineFragment : Fragment() {

    interface Callbacks

    private lateinit var backbutton: ImageButton
    private lateinit var helpbutton: ImageButton
    private lateinit var fotoMedicacion: ImageView
    private lateinit var tituloMedicacion: TextView
    private lateinit var FechaInicio: TextView
    private lateinit var FechaFin: TextView
    private lateinit var cantidadToma: TextView
    private lateinit var horasTomas: TextView
    var fecha : List<String> = emptyList()

    var medicacionPasada : Medicacion? = null

    private var callbacks: Callbacks? = null

    private val detailMedicineCitaViewModel: DetailMedicineViewModel by lazy {
        ViewModelProvider(this).get(DetailMedicineViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i(TAG, "Fragmento $TAG creado")
        medicacionPasada = arguments?.getSerializable(
                ARG_MEDICACION
        ) as Medicacion
        detailMedicineCitaViewModel.cargarMedicacion(medicacionPasada!!)


    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_medicine, container, false)


        fotoMedicacion = view.findViewById(R.id.imageMedicacion) as ImageView
        tituloMedicacion = view.findViewById(R.id.nombreMedicacion) as TextView
        FechaInicio = view.findViewById(R.id.fechaInicio) as TextView
        FechaFin = view.findViewById(R.id.fechaFinal) as TextView
        cantidadToma = view.findViewById(R.id.tomasTotales) as TextView
        horasTomas = view.findViewById(R.id.horaTomas) as TextView
        backbutton = view.findViewById(R.id.back_button_detail_medicine) as ImageButton

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        var horasAmostrar : String? = ""
        var diasAmostrar : String? = ""
        var horaNoDiaria : String? = ""

        val spfhours = SimpleDateFormat("HH:mm")
        val spfdays = SimpleDateFormat("EEEE")
        val spf = SimpleDateFormat("dd/MMM/yyyy")

        fotoMedicacion.setImageBitmap(detailMedicineCitaViewModel.medicacion.foto_medicacion)
        tituloMedicacion.text = detailMedicineCitaViewModel.medicacion.name
        FechaInicio.text = spf.format(detailMedicineCitaViewModel.medicacion.fecha_inicio).toString()
        FechaFin.text = spf.format(detailMedicineCitaViewModel.medicacion.fecha_fin).toString()
        cantidadToma.text = detailMedicineCitaViewModel.medicacion.numero_dosis.toString()


        if (detailMedicineCitaViewModel.medicacion.medicacion_diaria)
        {

            detailMedicineCitaViewModel.medicacion.tomas_diarias.forEach {
                val date = Date(it)
                fecha = fecha + spfhours.format(date)
            }

            detailMedicineCitaViewModel.medicacion.tomas_diarias.forEach {
                if (it != fecha[detailMedicineCitaViewModel.medicacion.tomas_diarias.size - 1])
                {
                    val d = Date(it)
                    horasAmostrar += " " + spfhours.format(d) + " "
                }
            }

            horasTomas.text = "Tomas diarias\n$horasAmostrar"
        }
        else
        {
            var i = 0
            horaNoDiaria = spfhours.format(Date(detailMedicineCitaViewModel.medicacion.tomas_diarias[0]))
            while (i < detailMedicineCitaViewModel.medicacion.tomas_mensuales) {
                val d = Date(detailMedicineCitaViewModel.medicacion.tomas_diarias[i])
                val dayOfTheWeek: String = spfdays.format(d)
                when (diasAmostrar)
                {
                    "Monday" -> diasAmostrar = "Lunes"
                    "Tuesday" -> diasAmostrar = "Martes"
                    "Wednesday" -> diasAmostrar = "Miercoles"
                    "Thursday" -> diasAmostrar = "Jueves"
                    "Friday" -> diasAmostrar = "Viernes"
                    "Saturday" -> diasAmostrar = "Sabado"
                    "Sunday" -> diasAmostrar = "Domingo"

                }
                diasAmostrar += "$dayOfTheWeek "
                i++
            }
            when (detailMedicineCitaViewModel.medicacion.tomas_mensuales) {
                1 -> horasTomas.text = "1 dia a la semana: \n $diasAmostrar $horaNoDiaria"
                2 -> horasTomas.text = "2 dias a la semana: \n $diasAmostrar $horaNoDiaria"
                3 -> horasTomas.text = "3 dias a la semana: \n $diasAmostrar $horaNoDiaria"
                5 -> horasTomas.text = "5 dias a la semana: \n $diasAmostrar $horaNoDiaria"
                15 -> horasTomas.text = "cada 15 dias: \n $diasAmostrar $horaNoDiaria"
                30 -> horasTomas.text = "1 vez al mes: \n $diasAmostrar $horaNoDiaria"
            }
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

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento")
    }

    companion object {
        /* Crea instancias de AjustesCitaFragment, recogiendo el valor que se le haya
        pasado como argumento, es decir, la cita de peluqueria pulsada
         */
        fun newInstance(medicacion: Medicacion): DetailMedicineFragment {
            val args = Bundle().apply {
                putSerializable(ARG_MEDICACION, medicacion as Serializable)
            }
            return DetailMedicineFragment()
                    .apply {
                        arguments = args
                    }
        }
    }

}
