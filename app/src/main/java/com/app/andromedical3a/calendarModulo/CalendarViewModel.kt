package com.app.andromedical3a.calendarModulo

import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository


class CalendarViewModel : ViewModel() {

    private val MedicacionRepositorio =
        MedicineRepository.get()

    val listaMedicacionLiveData = MedicacionRepositorio.getMedicaciones()


    private val CitaMedicoRepositorio =
        CitaMedicoRepository.get()

    val listaCitaMedicoLiveData = CitaMedicoRepositorio.getCitasMedico()


}
