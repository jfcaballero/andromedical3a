package com.app.andromedical3a.administrationMedicineModulo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.app.andromedical3a.Andromedical3aAplication
import java.util.*
import java.util.concurrent.Executors

private const val NOMBRE_BASE_DE_DATOS = "Medicacion-database"

class MedicineRepository private constructor(context: Context) {

    private val database: MedicacionDatabase = Room.databaseBuilder(
        context.applicationContext,
        MedicacionDatabase::class.java,
        NOMBRE_BASE_DE_DATOS
    ).build()

    private val medicacionDao = database.MedicacionDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getMedicaciones(): LiveData<List<Medicacion>> = medicacionDao.getAll()

    fun getMedicacion(id: UUID): LiveData<Medicacion> = medicacionDao.getById(id)

    fun updateMedicacion(medicacion : Medicacion) {
        executor.execute {
            medicacionDao.updateMedicacion(medicacion)
        }
    }

    fun addMedicacion(medicacion: Medicacion) {
        executor.execute {
            medicacionDao.insertMedicacion(medicacion)
        }
    }

    fun deleteMedicacion(medicacion: Medicacion) {
        executor.execute {
            medicacionDao.delete(medicacion)
        }
    }

    fun deleteMedicacionPorID(idCitaMedicacion: UUID) {
        executor.execute {
            medicacionDao.deleteMedicacionById(idCitaMedicacion)
        }
    }

    companion object {
        private var INSTANCE: MedicineRepository? = null

        fun inicializar(context : Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    MedicineRepository(
                        context
                    )
            }
        }

        fun get(): MedicineRepository {
            return INSTANCE
                ?:
                throw IllegalStateException("MedicineRepository debe ser inicializado")
        }
    }
}
