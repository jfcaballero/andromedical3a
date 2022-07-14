package com.app.andromedical3a.addCitaMedicoModulo;

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class CitaMedico(@PrimaryKey val id: UUID = UUID.randomUUID(),
                      var nombreMedico: String,
                      var fecha_cita: Date,
                      var hora_alarma: Date,
                      var informacion_cita: String,
                      var id_alarma :  String
): Serializable



