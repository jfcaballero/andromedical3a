package com.app.andromedical3a.administrationCitaMedicoModulo

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface CitaMedicoDao {
    @Query("SELECT * FROM citamedico")
    fun getAll(): LiveData<List<CitaMedico>>

    @Query("SELECT * FROM citamedico WHERE id = (:id)")
    fun getById(id: Int?): LiveData<CitaMedico>

    @Query("SELECT * FROM citamedico WHERE nombreMedico LIKE :nombreMedico")
    fun findByName(nombreMedico: String): CitaMedico

    @Update
    fun updateCitaMedico(citamedico: CitaMedico)

    @Insert
    fun insertCitaMedico(citamedico: CitaMedico)

    @Delete
    fun delete(citamedico: CitaMedico)

    @Query("DELETE FROM citamedico WHERE nombreMedico=(:nombreMedico)")
    fun deleteCitaMedico(nombreMedico: String)

    @Query("DELETE FROM citamedico WHERE id=(:id)")
    fun deleteCitaMedicoById(id: UUID?)
}