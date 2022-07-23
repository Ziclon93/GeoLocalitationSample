package com.example.freenowtest.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vehicle")
data class VehicleEntity(
    @PrimaryKey var id: Int,
    var latitude: Float,
    val longitude: Float,
    val fleetType: String,
    val heading: Float,
)
