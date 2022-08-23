package com.app.andromedical3a.administrationAlarmCitaMedica

import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
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