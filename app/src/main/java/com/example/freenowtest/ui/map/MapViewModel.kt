package com.example.freenowtest.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.domain.usecase.GetVehicleCatalogUseCase
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getVehicleCatalogUseCase: GetVehicleCatalogUseCase,
) : ViewModel() {
    val vehicleMap = MutableLiveData<MutableMap<Vehicle, Marker?>?>()
    var selectedMarker: Marker? = null

    fun loadVehicleCatalog() {
        val vehicleCatalog = getVehicleCatalogUseCase.getVehicleCatalog()

        if (vehicleCatalog.isEmpty()) requestVehicleCatalog()
        else {
            vehicleMap.value =
                vehicleCatalog.map { vehicle -> Pair(vehicle, null) }.toMap().toMutableMap()
        }
    }

    private fun requestVehicleCatalog() {
        getVehicleCatalogUseCase.execute(
            onSuccess = { vehicleList ->
                vehicleMap.value =
                    vehicleList.map { vehicle -> Pair(vehicle, null) }.toMap().toMutableMap()
            },
            onError = { vehicleMap.value = null },
        )
    }

    fun addMarker(vehicle: Vehicle, marker: Marker) = vehicleMap.value?.put(vehicle, marker)

}