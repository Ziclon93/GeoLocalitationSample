package com.example.freenowtest.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.freenowtest.data.source.local.dao.VehicleDao
import com.example.freenowtest.data.source.local.entity.VehicleEntity

@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val vehicleDao: VehicleDao

    companion object {
        const val DB_NAME = "VehicleCatalogDatabase.db"
    }
}