package com.app.andromedical3a.addMedicationModulo;

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Medicacion(@PrimaryKey val id: UUID = UUID.randomUUID(),
        val name: String,
        var fecha_inicio: Date,
        var fecha_fin: Date,
        //Numero de dosis que trae la medicación
        var numero_dosis: Float,
        //Cantidad de medicación en cada toma
        var comentario_dosis: String,
        var tomas_diarias: List<String>,
        var foto_medicacion: Bitmap,
        var medicacion_diaria : Boolean
): Serializable



