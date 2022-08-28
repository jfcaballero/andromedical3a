package com.app.andromedical3a.calendarModulo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.andromedical3a.R
import kotlinx.coroutines.flow.merge
import org.naishadhparmar.zcustomcalendar.CustomCalendar
import org.naishadhparmar.zcustomcalendar.Property
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


private const val TAG = "CitaMedico"
private const val ARG_MEDICACION = "medicacion"

class CalendarFragment : Fragment() {

    interface Callbacks {
    }


    private lateinit var backbutton: ImageButton
    private lateinit var helpbutton: ImageButton
    private lateinit var calendar : CustomCalendar


    private val calendarViewModel: CalendarViewModel by lazy {
        ViewModelProvider(this).get(CalendarViewModel::class.java)
    }

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)


        calendar = view.findViewById(R.id.calendar) as CustomCalendar

        backbutton = view.findViewById(R.id.back_button_administration_citaMedico) as ImageButton
        helpbutton = view.findViewById(R.id.help_icon_administration_citaMedico) as ImageButton



        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar.setNavigationButtonEnabled(CustomCalendar.PREVIOUS,false)
        calendar.setNavigationButtonEnabled(CustomCalendar.NEXT,false)

        calendarViewModel.listaMedicacionLiveData.observe(
            viewLifecycleOwner
        ) { medicaciones ->
            medicaciones?.let {
                Log.d(TAG, "Numero de medicamentos : ${medicaciones.size}")

                //Unimos todas las tomas, para pintar el calendario
                val tomas_realizadas : MutableList<String> = arrayListOf()
                var fecha_inicio_menor = Date()
                var fecha_final_mayor  = Date()

                medicaciones.forEach { medicacion ->
                    tomas_realizadas += medicacion.tomas_realizadas

                    if (medicacion.fecha_inicio < fecha_inicio_menor)
                        fecha_inicio_menor = medicacion.fecha_inicio

                    if (medicacion.fecha_fin > fecha_final_mayor)
                        fecha_final_mayor = medicacion.fecha_fin

                    }
                    //Ordenamos lista
                    tomas_realizadas.sort()

                    val descHashMap: HashMap<Any, Property> = HashMap()

                    val medicacionNoTomada = Property()
                    medicacionNoTomada.layoutResource = R.layout.present_view
                    medicacionNoTomada.dateTextViewResource = R.id.text_view
                    descHashMap.put("present", medicacionNoTomada)

                    val medicacionTomada: Property = Property()
                    medicacionTomada.layoutResource = R.layout.absent_view
                    medicacionTomada.dateTextViewResource = R.id.text_view
                    descHashMap.put("absent", medicacionTomada)

                    calendar.setMapDescToProp(descHashMap)

                    val calendarSave = Calendar.getInstance()

                    val dateHashmap: HashMap<Int, Any> = HashMap()

                    val hoy: Calendar = Calendar.getInstance()

                    fecha_inicio_menor.hours = 0
                    fecha_inicio_menor.minutes = 0
                    fecha_inicio_menor.seconds = 0
                    hoy.time = fecha_inicio_menor
                    //Pongo a amarillo todas las fechas hastas los tres meses siguientes
                    while (hoy.time.before(fecha_final_mayor)) {
                        if (hoy.time.month == calendarSave.time.month) {
                            dateHashmap.put(hoy.time.date,"absent")
                        }

                        hoy.add(Calendar.DATE, 1)
                    }

                    calendar.setDate(calendarSave,dateHashmap);

                    hoy.time = fecha_inicio_menor
                    fecha_final_mayor.hours = 0
                    fecha_final_mayor.minutes = 0
                    fecha_final_mayor.seconds = 0
                    //Pongo a amarillo todas las fechas hastas los tres meses siguientes
                    while (hoy.time.before(fecha_final_mayor)) {
                        tomas_realizadas.forEach {
                            val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                            val fecha = sdf.parse(it)
                            val horaToma= sdf.parse(hoy.time.toString())
                            fecha.hours = 0
                            fecha.minutes = 0
                            fecha.seconds = 0
                            if (fecha.equals(horaToma))
                            {
                                //Controlamos que si existe algun false en el dia actual, no cambie
                                // el color del calendario
                                if (it.contains("false"))
                                    dateHashmap.put(hoy.time.date,"absent")

                                if (it.contains("true"))
                                    dateHashmap.put(hoy.time.date,"present")
                            }
                        }

                        hoy.add(Calendar.DATE, 1)
                    }

                    calendar.setDate(calendarSave,dateHashmap);


            }
        }

    }


    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()

        backbutton.setOnClickListener {
            Log.i(TAG, "Saliendo de fragmento $TAG")
            activity?.onBackPressed()
        }

        helpbutton.setOnClickListener {
            val dlgAlert = AlertDialog.Builder(this.context)
            dlgAlert.setTitle("VENTANA DE AYUDA")
            dlgAlert.setMessage("Esta en el CALENDARIO, aquí se muestra si existe alguna medicación para tomar en el mes. \n\n")
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }

    }


    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Destruyendo fragmento")
    }

}
