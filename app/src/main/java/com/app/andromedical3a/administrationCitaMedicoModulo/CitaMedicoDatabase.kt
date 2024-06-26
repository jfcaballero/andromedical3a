package com.app.andromedical3a.administrationCitaMedicoModulo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [CitaMedico::class], version = 1, exportSchema = false)
@TypeConverters(CitaMedicoTypeConverters::class)
abstract class CitaMedicoDatabase : RoomDatabase() {

    abstract fun CitaMedicoDao(): CitaMedicoDao

}