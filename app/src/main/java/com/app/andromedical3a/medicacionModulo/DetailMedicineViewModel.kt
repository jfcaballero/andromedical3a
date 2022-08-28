package com.app.andromedical3a.medicacionModulo

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.app.andromedical3a.administrationMedicineModulo.Medicacion
import com.app.andromedical3a.administrationMedicineModulo.MedicineRepository
import java.util.*


class DetailMedicineViewModel : ViewModel() {

    var w: Int = 10;
    var h:Int = 10

    var conf = Bitmap.Config.ARGB_8888 // see other conf types

    var bmp = Bitmap.createBitmap(w, h, conf)

    private val MedicacionRepositorio =
        MedicineRepository.get()
    var medicacion =
        Medicacion(UUID.randomUUID(),
            "",
            Date(),
            Date(),
            0.0f,
            0.0f,
            "",
            emptyList(),bmp,false,0,emptyList(), emptyList()
        )
    fun cargarMedicacion(medicacion: Medicacion)
    {
        this.medicacion = medicacion
    }

    fun deleteMedicacionById(idMedicacion: UUID?)
    {
        MedicacionRepositorio.deleteMedicacionPorID(idMedicacion)
    }
}
