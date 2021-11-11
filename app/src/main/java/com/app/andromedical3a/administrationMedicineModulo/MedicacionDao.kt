package com.app.andromedical3a.administrationMedicineModulo

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.andromedical3a.addMedicationModulo.Medicacion
import java.util.*

@Dao
interface MedicacionDao {
    @Query("SELECT * FROM medicacion")
    fun getAll(): LiveData<List<Medicacion>>

    @Query("SELECT * FROM medicacion WHERE id = (:id)")
    fun getById(id: UUID): LiveData<Medicacion>

    @Query("SELECT * FROM medicacion WHERE name LIKE :name")
    fun findByName(name: String): Medicacion

    @Update
    fun updateMedicacion(medicacion: Medicacion)

    @Insert
    fun insertMedicacion(medicacion: Medicacion)

    @Delete
    fun delete(medicacion: Medicacion)

    @Query("DELETE FROM medicacion WHERE name=(:nameMedicacion)")
    fun deleteMedicacion(nameMedicacion: String)

    @Query("DELETE FROM medicacion WHERE id=(:id)")
    fun deleteMedicacionById(id: UUID)
}