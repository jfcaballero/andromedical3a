package com.app.andromedical3a.addMedicationModulo

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository
import java.util.*


class AddMedicacionViewModel : ViewModel(){

    private val medicacionRepositorio =
        MedicineRepository.get()
    private var nombreMedicacion = MutableLiveData("")
    private var horasTomas = MutableLiveData<ArrayList<String>>()
    private var cantidadTotal = MutableLiveData(0.0.toFloat())
    private var cantidadToma = MutableLiveData(0.0.toFloat())
    private var comentarioTomas = MutableLiveData("")
    private var fotoMedicacion = MutableLiveData<Bitmap>()
    private var fechaInicio = MutableLiveData<Date>()
    private var fechaFinal = MutableLiveData<Date>()
    private var idsAlarmas = MutableLiveData<ArrayList<String>>()

    //Medicacion no diaria
    private var medicacionDiaria = MutableLiveData(false)
    private var numeroTomasMensuales = MutableLiveData(0)
    private var diasMedicacionMensual =  MutableLiveData<ArrayList<String>>()

    var ValuenombreMedicacion : MutableLiveData<String> = nombreMedicacion
    var ValueoidsAlarmas : MutableLiveData<ArrayList<String>> = idsAlarmas
    var ValuehoraTomasTotales : MutableLiveData<ArrayList<String>> = horasTomas
    var ValueCantidadTotal : MutableLiveData<Float> = cantidadTotal
    var ValueCantidadToma : MutableLiveData<Float> = cantidadToma
    var ValueComentarioTomas : MutableLiveData<String> = comentarioTomas
    var ValueFotoMedicacion : MutableLiveData<Bitmap> = fotoMedicacion
    var ValueFechaInicio : MutableLiveData<Date> = fechaInicio
    var ValueFechaFinal : MutableLiveData<Date> = fechaFinal

    //Medicacion no diaria
    var ValueMedicacionDiaria : MutableLiveData<Boolean> = medicacionDiaria
    var ValueTomasMensuales : MutableLiveData<Int> = numeroTomasMensuales
    var ValueDiasMedicacionMensual : MutableLiveData<ArrayList<String>> = diasMedicacionMensual

    init {
        ValuehoraTomasTotales.value = arrayListOf()
        ValueDiasMedicacionMensual.value = arrayListOf()
        ValueoidsAlarmas.value = arrayListOf()
    }

    fun setCantidadToma(total: Float)
    {
        ValueCantidadToma.value = total
    }

    fun sethoraTomasTotales(horas: String)
    {
        ValuehoraTomasTotales.value?.add(horas)
    }

    fun setComentarioTomas(comentario: String)
    {
        ValueComentarioTomas.value = comentario;
    }

    fun setFotoMedicacion(foto: Bitmap)
    {
        ValueFotoMedicacion.value = foto;
    }

    fun setFechaInicio(fechaInicio: Date)
    {
        ValueFechaInicio.value = fechaInicio;
    }

    fun setFechaFinal(fechaFinal: Date)
    {
        ValueFechaFinal.value = fechaFinal;
    }

    fun setMedicacionDiaria(medicacion: Boolean) {
        ValueMedicacionDiaria.value = medicacion;
    }

    fun setNombreMedicacion(nombre: String){
        ValuenombreMedicacion.value = nombre
    }

    fun setTomasMensuales(tomas: Int){
        ValueTomasMensuales.value = tomas
    }


    fun getDaysBetweenDatesOneWeek(startdate: Date?, enddate: Date?, daysOfWeek: ArrayList<String>){
        val calendar: Calendar = Calendar.getInstance()
        val original = Date(ValuehoraTomasTotales.value?.get(0))
        //reset value horas totales, to delete value duplicate
        ValuehoraTomasTotales.value?.clear()
        val hours = Date(original.time)
        calendar.time = startdate
        while (calendar.time.before(enddate)) {
            val result = calendar.time
            for (day in daysOfWeek) {
                if (result.day.toString() == day) {
                    result.hours = hours.hours
                    result.minutes = hours.minutes
                    sethoraTomasTotales(result.toString())
                }
            }
            calendar.add(Calendar.DATE, 1)
        }
    }

    fun getDaysBetweenDatesMonth(startdate: Date?, enddate: Date?, daysOfWeek: ArrayList<String>){
        val original = Date(ValuehoraTomasTotales.value?.get(0))
        //reset value horas totales, to delete value duplicate
        ValuehoraTomasTotales.value?.clear()
        val hours = Date(original.time)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = startdate
        while (calendar.time.before(enddate)) {
            val result = calendar.time
                if (result.day.toString() == daysOfWeek[0]){
                    result.hours = hours.hours
                    result.minutes = hours.minutes
                    sethoraTomasTotales(result.toString())
                }
            calendar.add(Calendar.DATE, ValueTomasMensuales.value!!)
        }
    }

    fun setidsAlarmass(ids: String)
    {
        ValueoidsAlarmas.value?.add(ids)
    }

    fun setDiasTomasMensuales(dias: String)
    {
        ValueDiasMedicacionMensual.value?.add(dias)
    }

    fun addMedicationToBBDDConTomasMensuales(): Medicacion {
        when (ValueTomasMensuales.value){
            1, 2, 3, 5 -> getDaysBetweenDatesOneWeek(ValueFechaInicio.value, ValueFechaFinal.value, ValueDiasMedicacionMensual.value!!)
            15, 30 -> getDaysBetweenDatesMonth(ValueFechaInicio.value, ValueFechaFinal.value, ValueDiasMedicacionMensual.value!!)
        }

        val medicacion = Medicacion(ValuenombreMedicacion.value!!, ValueFechaInicio.value!!, ValueFechaFinal.value!!, ValueCantidadTotal.value!!,ValueCantidadToma.value!!, ValueComentarioTomas.value!!, ValuehoraTomasTotales.value!!, ValueFotoMedicacion.value!!, ValueMedicacionDiaria.value!!,ValueTomasMensuales.value!!,ValueoidsAlarmas.value!!)
        medicacionRepositorio.addMedicacion(medicacion)

        return medicacion
    }

    fun addMedicacionABBDD(): Medicacion {

        val medicacion = Medicacion(ValuenombreMedicacion.value!!, ValueFechaInicio.value!!, ValueFechaFinal.value!!, ValueCantidadTotal.value!!,ValueCantidadToma.value!!, ValueComentarioTomas.value!!, ValuehoraTomasTotales.value!!, ValueFotoMedicacion.value!!, ValueMedicacionDiaria.value!!,ValueTomasMensuales.value!!,ValueoidsAlarmas.value!!)
        medicacionRepositorio.addMedicacion(medicacion)

        return medicacion
    }
}

