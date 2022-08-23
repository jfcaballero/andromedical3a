package com.app.andromedical3a.administrationMedicineModulo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Medicacion::class], version = 1, exportSchema = false)
@TypeConverters(MedicacionTypeConverters::class)
abstract class MedicacionDatabase : RoomDatabase() {

    abstract fun MedicacionDao(): MedicacionDao

}