package com.app.andromedical3a

import android.app.Application
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository

class Andromedical3aAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MedicineRepository.inicializar(this)
    }
}