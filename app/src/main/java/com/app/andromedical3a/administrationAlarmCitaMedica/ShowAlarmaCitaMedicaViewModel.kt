package com.app.andromedical3a.administrationAlarmCitaMedica

import android.app.*
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.app.andromedical3a.addCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.addMedicationModulo.Medicacion
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository
import java.util.*

private const val TAG = "AlarmCitaMedicoService"

private val citaMedicoRepository =
    CitaMedicoRepository.get()
class ShowAlarmaCitaMedicaViewModel : ViewModel() {

    var citaMedica =
        CitaMedico(UUID.randomUUID(),
            "",
            Date(),
            Date(),
            "",
            ""
        )

}