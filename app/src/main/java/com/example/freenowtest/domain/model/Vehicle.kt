package com.example.freenowtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vehicle(
    val id: Int,
    val coordinate: Coordinate,
    val location: Location?,
    val fleetType: FleetType,
    val heading: Float,
    val orientation: Orientation?,
): Parcelable