package com.app.andromedical3a.mainApplication

import android.app.Application
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository

class Andromedical3aAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MedicineRepository.inicializar(this)
        CitaMedicoRepository.inicializar(this)
    }
}