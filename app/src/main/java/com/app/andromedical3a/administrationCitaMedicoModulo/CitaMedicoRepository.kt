package com.app.andromedical3a.administrationCitaMedicoModulo

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val NOMBRE_BASE_DE_DATOS = "CitaMedico-database"
private const val TAG = "CitaMedico-database"

class CitaMedicoRepository private constructor(context: Context) {

    private val database: CitaMedicoDatabase = Room.databaseBuilder(
        context.applicationContext,
            CitaMedicoDatabase::class.java,
        NOMBRE_BASE_DE_DATOS
    ).build()

    private val citamedicoDao = database.CitaMedicoDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCitasMedico(): LiveData<List<CitaMedico>> = citamedicoDao.getAll()

    fun getCitaMedico(id: Int?): LiveData<CitaMedico> = citamedicoDao.getById(id)

    fun updateCitaMedico(citaMedico : CitaMedico) {
        executor.execute {
            citamedicoDao.updateCitaMedico(citaMedico)
        }
    }

    fun addCitaMedico(citaMedico: CitaMedico) {
        executor.execute {
            citamedicoDao.insertCitaMedico(citaMedico)
        }
    }


    fun deleteCitaMedico(citaMedico: CitaMedico) {
        executor.execute {
            citamedicoDao.delete(citaMedico)
        }
    }

    fun deleteCitaMedicoPorID(idCitaMedico: UUID?) {
        executor.execute {
            citamedicoDao.deleteCitaMedicoById(idCitaMedico)
        }
    }

    companion object {
        private var INSTANCE: CitaMedicoRepository? = null

        fun inicializar(context : Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    CitaMedicoRepository(
                        context
                    )
            }
        }

        fun get(): CitaMedicoRepository {
            return INSTANCE
                ?:
                throw IllegalStateException("CitaMedicoRepository debe ser inicializado")
        }
    }
}
