package com.app.andromedical3a.administrationMedicineModulo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val NOMBRE_BASE_DE_DATOS = "Medicacion-database"
private const val TAG = "Medicacion-database"

class MedicineRepository private constructor(context: Context) {

    private val database: MedicacionDatabase = Room.databaseBuilder(
        context.applicationContext,
        MedicacionDatabase::class.java,
        NOMBRE_BASE_DE_DATOS
    ).build()

    private val medicacionDao = database.MedicacionDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getMedicaciones(): LiveData<List<Medicacion>> = medicacionDao.getAll()

    fun getMedicacion(id: Int?): LiveData<Medicacion> = medicacionDao.getById(id)

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

    fun setMedicacionByNameAndAddMedicacion(name : String,sumar : Float) {
        executor.execute {
            val medicacion = medicacionDao.findByName(name)
            medicacion.numero_dosis = medicacion.numero_dosis + sumar
            medicacionDao.updateMedicacion(medicacion)
        }

    }

    fun deleteMedicacion(medicacion: Medicacion) {
        executor.execute {
            medicacionDao.delete(medicacion)
        }
    }

    fun deleteMedicacionPorID(idCitaMedicacion: UUID?) {
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
