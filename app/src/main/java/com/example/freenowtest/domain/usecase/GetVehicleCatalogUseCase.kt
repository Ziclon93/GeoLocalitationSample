package com.example.freenowtest.domain.usecase

import com.example.freenowtest.data.repository.VehicleCatalogRepositoryImp
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetVehicleCatalogUseCase @Inject constructor(
    private val vehicleCatalogService: VehicleCatalogRepositoryImp
) : SingleUseCase<List<Vehicle>>() {

    override fun buildUseCaseSingle(): Single<List<Vehicle>> {
        return vehicleCatalogService.requestVehicleCatalog()
    }

    fun getVehicleCatalog(): List<Vehicle> = vehicleCatalogService.getVehicleCatalog()

}