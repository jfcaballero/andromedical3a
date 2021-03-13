package com.app.andromedical3a.administrationMedicineModulo;

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable
import java.util.*

@Entity(primaryKeys = ["id","name"])
data class Medicacion(
        val id: UUID = UUID.randomUUID(),
        val name : String,
        @ColumnInfo(name = "fecha_inicio") var fecha_inicio: Date,
        @ColumnInfo(name = "fecha_fin") var fecha_fin: Date,
        //Numero de dosis que trae la medicación
        @ColumnInfo(name = "numero_dosis") var numero_dosis: Int,
        //Cantidad de medicación en cada toma
        @ColumnInfo(name = "cantidad_dosis") var cantidad_dosis: Float,
        @ColumnInfo(name = "calendario_tomas") var tomas_diarias: Calendar,
        @ColumnInfo(typeAffinity  = ColumnInfo.BLOB, name = "foto_medicacion") var foto_medicacion : Bitmap
): Serializable {

}



