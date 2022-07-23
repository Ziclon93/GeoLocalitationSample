package com.example.freenowtest.data.source.remote.response

import kotlinx.serialization.SerialName

data class VehicleCatalogResponse(
    @SerialName("poiList") val poiList: List<VehicleResponse>,
)