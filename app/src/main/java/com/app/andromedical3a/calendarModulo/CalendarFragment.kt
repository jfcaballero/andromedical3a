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
import org.naishadhparmar.zcustomcalendar.CustomCalendar
import org.naishadhparmar.zcustomcalendar.Property
import java.text.SimpleDateFormat
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

                medicaciones.forEach {
                    medicacion ->
                    val inicio : Date = medicacion.fecha_inicio
                    val fin : Date = medicacion.fecha_fin

                    val descHashMap: HashMap<Any, Property> = HashMap()

                    val medicacionNoTomada = Property()
                    medicacionNoTomada.layoutResource = R.layout.present_view
                    medicacionNoTomada.dateTextViewResource= R.id.text_view
                    descHashMap.put("present", medicacionNoTomada)

                    val medicacionTomada  : Property = Property()
                    medicacionTomada.layoutResource = R.layout.absent_view
                    medicacionTomada.dateTextViewResource= R.id.text_view
                    descHashMap.put("absent",medicacionTomada)

                    calendar.setMapDescToProp(descHashMap)

                    val calendarSave = Calendar.getInstance()

                    val dateHashmap: HashMap<Int, Any> = HashMap()

                    val hoy: Calendar = Calendar.getInstance()

                    hoy.time = inicio
                    //Pongo a amarillo todas las fechas hastas los tres meses siguientes
                    while (hoy.time.before(fin)) {
                        if (hoy.time.month == calendarSave.time.month) {
                            dateHashmap.put(hoy.time.date,"absent")
                        }
                        hoy.add(Calendar.DATE, 1)
                    }

                    calendar.setDate(calendarSave,dateHashmap);

                    if (medicacion.medicacion_diaria)
                    {
                        medicacion.tomas_diarias.forEach{
                            val  diaActual = Calendar.getInstance()

                            if (diaActual.time > Date(it))
                                dateHashmap[diaActual.time.date] = "present"
                        }

                        calendar.setDate(calendarSave,dateHashmap);
                    }
                    else
                    {

                        medicacion.tomas_diarias.forEach {
                            val sdf = SimpleDateFormat("EEE MMM dd", Locale.ENGLISH)
                            val  diaActual = Calendar.getInstance()
                            diaActual.time = sdf.parse(diaActual.time.toString())
                            val fechaMedicacion = sdf.parse(it)
                            if (diaActual.time.equals(fechaMedicacion))
                            {
                                val  diaActualyHora = Calendar.getInstance()
                                if (diaActualyHora.time > Date(it))
                                    dateHashmap[diaActual.time.date] = "present"
                            }
                        }
                        calendar.setDate(calendarSave,dateHashmap);
                    }
                }

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
