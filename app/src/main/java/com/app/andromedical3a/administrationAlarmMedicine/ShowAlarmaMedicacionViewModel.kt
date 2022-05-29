package com.app.andromedical3a.administrationAlarmMedicine

import android.app.*
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.app.andromedical3a.addMedicationModulo.Medicacion
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository
import java.util.*

private const val TAG = "AlarmService"

private val medicacionRepositorio =
    MedicineRepository.get()
class ShowAlarmaMedicacionViewModel : ViewModel() {

    var w: Int = 10;
    var h:Int = 10

    var conf = Bitmap.Config.ARGB_8888 // see other conf types

    var bmp = Bitmap.createBitmap(w, h, conf)

    var medicacion =
        Medicacion(
            "",
            Date(),
            Date(),
            0.0f,
            0.0f,
            "",
            emptyList(),bmp,false, 0,emptyList()
        )

    fun sumarMedicacionTotal(name : String,sumar : Float) {
    medicacionRepositorio.setMedicacionByNameAndAddMedicacion(name,sumar)
    }

}