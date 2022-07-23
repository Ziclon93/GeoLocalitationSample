package com.example.freenowtest.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.freenowtest.data.source.local.entity.VehicleEntity

@Dao
interface VehicleDao {

    @Query("SELECT * FROM Vehicle")
    fun loadAll(): MutableList<VehicleEntity>

    @Query("DELETE FROM Vehicle")
    fun deleteAll()

    @Insert
    @JvmSuppressWildcards
    fun insertAll(objects: List<VehicleEntity>)

}
