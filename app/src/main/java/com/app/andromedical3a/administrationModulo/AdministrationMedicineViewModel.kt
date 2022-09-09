package com.app.andromedical3a.administrationModulo

import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationMedicineModulo.Medicacion
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository


class AdministrationMedicineViewModel : ViewModel() {

    private val MedicacionRepositorio =
        MedicineRepository.get()
    val listaMedicacionLiveData = MedicacionRepositorio.getMedicaciones()

}
