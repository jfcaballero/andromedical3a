package com.app.andromedical3a.administrationModulo

import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository


class AdministrationCitaMedicoViewModel : ViewModel() {

    private val CitaMedicoRepositorio =
            CitaMedicoRepository.get()
    val listaCitaMedicoLiveData = CitaMedicoRepositorio.getCitasMedico()

}
