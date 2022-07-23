package com.example.freenowtest.domain.repository

import com.example.freenowtest.domain.model.Vehicle
import io.reactivex.Single

interface VehicleCatalogRepository {
    fun getVehicleCatalog(): List<Vehicle>
    fun requestVehicleCatalog(): Single<List<Vehicle>>
    fun putVehicleCatalog(vehicleList: List<Vehicle>)
}