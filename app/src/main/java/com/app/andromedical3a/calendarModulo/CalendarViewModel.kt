package com.app.andromedical3a.calendarModulo

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.app.andromedical3a.addCitaMedicoModulo.CitaMedico
import com.app.andromedical3a.addMedicationModulo.Medicacion
import com.app.andromedical3a.administrationCitaMedicoModulo.CitaMedicoRepository
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository
import java.util.*


class CalendarViewModel : ViewModel() {

    var w: Int = 10;
    var h:Int = 10

    var conf = Bitmap.Config.ARGB_8888 // see other conf types

    var bmp = Bitmap.createBitmap(w, h, conf)

    private val MedicacionRepositorio =
        MedicineRepository.get()

    val listaMedicacionLiveData = MedicacionRepositorio.getMedicaciones()





}
