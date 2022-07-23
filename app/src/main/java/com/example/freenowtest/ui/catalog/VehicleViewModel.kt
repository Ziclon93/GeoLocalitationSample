package com.example.freenowtest.ui.catalog

import androidx.lifecycle.MutableLiveData
import com.example.freenowtest.domain.model.Vehicle

class VehicleViewModel(
    val vehicle: Vehicle,
) {
    private val vehicleData = MutableLiveData<Vehicle>()
    val location: MutableLiveData<String> = MutableLiveData("Unknown")
    val heading: MutableLiveData<String> = MutableLiveData("Unknown")


    init {
        vehicleData.value = vehicle
        location.value =
            "${vehicle.location?.address}, ${vehicle.location?.city}, ${vehicle.location?.state}, ${vehicle.location?.country}"
        heading.value = vehicle.orientation?.type
    }
}