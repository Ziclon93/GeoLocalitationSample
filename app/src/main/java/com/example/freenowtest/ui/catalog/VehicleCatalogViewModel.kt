package com.example.freenowtest.ui.catalog

import androidx.lifecycle.*
import com.example.freenowtest.domain.model.Vehicle
import com.example.freenowtest.domain.usecase.GetVehicleCatalogUseCase
import com.example.freenowtest.domain.usecase.LoadVehicleResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@HiltViewModel
class VehicleCatalogViewModel @Inject constructor(
    val loadVehicleResourcesUseCase: LoadVehicleResourcesUseCase,
    private val getVehicleCatalogUseCase: GetVehicleCatalogUseCase,
) : ViewModel() {

    val vehicles: MutableLiveData<List<Vehicle>?> = MutableLiveData<List<Vehicle>?>()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun loadVehicleCatalog() {
        val vehicleCatalog = getVehicleCatalogUseCase.getVehicleCatalog()

        if (vehicleCatalog.isEmpty()) requestVehicleCatalog()
        else {
            vehicles.value = vehicleCatalog
            isLoading.value = false
        }
    }

    private fun requestVehicleCatalog() {
        getVehicleCatalogUseCase.execute(
            onSuccess = {
                vehicles.value = it
                isLoading.value = false
            },
            onError = {
                vehicles.value = null
                isLoading.value = true
            },
        )
    }

    init {
        isLoading.value = true
    }
}