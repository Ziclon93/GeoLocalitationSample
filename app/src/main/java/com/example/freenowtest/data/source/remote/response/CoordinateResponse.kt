package com.example.freenowtest.data.source.remote.response

import kotlinx.serialization.SerialName

data class CoordinateResponse(
    @SerialName("latitude") val latitude: Float,
    @SerialName("longitude") val longitude: Float,
)