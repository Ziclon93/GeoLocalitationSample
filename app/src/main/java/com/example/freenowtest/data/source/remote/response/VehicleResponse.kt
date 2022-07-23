package com.example.freenowtest.data.source.remote.response

import kotlinx.serialization.SerialName

data class VehicleResponse(
    @SerialName("id") val id: Int,
    @SerialName("coordinate") val coordinate: CoordinateResponse,
    @SerialName("fleetType") val fleetType: String,
    @SerialName("heading") val heading: Float,
)