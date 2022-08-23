package com.app.andromedical3a.citaMedicoModulo

import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
import java.util.*


class DetailCitaMedicoViewModel : ViewModel() {

    private val CitaMedicoRepositorio =
        CitaMedicoRepository.get()
    var citaMedico =
        CitaMedico(UUID.randomUUID(),
            "",
            Date(),
            Date(),
            "",
            "",
        )
    fun cargarCitaMedica(citaMedico: CitaMedico)
    {
        this.citaMedico = citaMedico
    }

    fun deleteCitaMedicoById(idCitaMedico: UUID?)
    {
        CitaMedicoRepositorio.deleteCitaMedicoPorID(idCitaMedico)
    }
}
