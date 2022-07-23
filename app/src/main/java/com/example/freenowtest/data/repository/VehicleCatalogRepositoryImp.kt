package com.example.freenowtest.data.repository

import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.data.mapper.toEntity
import com.example.freenowtest.data.mapper.toVehicleCatalog
import com.example.freenowtest.data.mapper.toVehicleList
import com.example.freenowtest.data.source.local.AppDatabase
import com.example.freenowtest.data.source.remote.ApiEndpoints
import com.example.freenowtest.domain.repository.VehicleCatalogRepository
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.util.Constants.HAMBURG_LAT_P1
import com.example.freenowtest.util.Constants.HAMBURG_LAT_P2
import com.example.freenowtest.util.Constants.HAMBURG_LON_P1
import com.example.freenowtest.util.Constants.HAMBURG_LON_P2
import io.reactivex.Single

class VehicleCatalogRepositoryImp(
    private val endpoints: ApiEndpoints,
    private val appDatabase: AppDatabase,
    private val locationService: LocationServiceImp,
) : VehicleCatalogRepository {

    override fun requestVehicleCatalog(): Single<List<Vehicle>> = endpoints.getVehicles(
        HAMBURG_LAT_P1, HAMBURG_LON_P1, HAMBURG_LAT_P2, HAMBURG_LON_P2,
    ).map { vehicleResponseList ->
        vehicleResponseList.toVehicleList(locationService).sortedWith(compareBy { it.id })
    }.doOnSuccess { putVehicleCatalog(it) }

    override fun getVehicleCatalog(): List<Vehicle> =
        appDatabase.vehicleDao.loadAll().toVehicleCatalog(locationService)


    override fun putVehicleCatalog(vehicleList: List<Vehicle>) {
        appDatabase.vehicleDao.insertAll(vehicleList.toEntity())
    }
}