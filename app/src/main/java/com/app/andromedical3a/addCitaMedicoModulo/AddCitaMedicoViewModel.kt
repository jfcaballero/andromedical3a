package com.app.andromedical3a.addCitaMedicoModulo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
import java.util.*


class AddCitaMedicoViewModel : ViewModel(){

    private val citaMedicoRepositorio =
        CitaMedicoRepository.get()
    private var nombreMedico = MutableLiveData("")
    private var comentariosCitaMedico = MutableLiveData("")
    private var fechaAlarma = MutableLiveData<Date>()
    private var fechaCita = MutableLiveData<Date>()
    private var idAlarma = MutableLiveData("")



    var ValueNombreMedico : MutableLiveData<String> = nombreMedico
    var ValueComentariosCitaMedico : MutableLiveData<String> = comentariosCitaMedico
    var ValueFechaAlarma : MutableLiveData<Date> = fechaAlarma
    var ValueFechaCita : MutableLiveData<Date> = fechaCita
    var ValueoIdAlarma : MutableLiveData<String> = idAlarma


    fun setNombreMedico(nombreMedico: String)
    {
        ValueNombreMedico.value = nombreMedico
    }

    fun setComentariosCitaMedico(comentariosCitaMedico: String)
    {
        ValueComentariosCitaMedico.value = comentariosCitaMedico
    }

    fun setFechaAlarma(fechaAlarma: Date)
    {
        ValueFechaAlarma.value = fechaAlarma;
    }

    fun setFechaCita(fechaCita: Date)
    {
        ValueFechaCita.value = fechaCita;
    }

    fun setIdAlarma(id: String)
    {
        ValueoIdAlarma.value = id
    }

    fun addCitaMedicoBBDD(): CitaMedico {

        val citaMedico = CitaMedico(UUID.randomUUID(),ValueNombreMedico.value!!, ValueFechaCita.value!!, ValueFechaAlarma.value!!, ValueComentariosCitaMedico.value!!,ValueoIdAlarma.value!!)
        citaMedicoRepositorio.addCitaMedico(citaMedico)

        return citaMedico
    }

}

