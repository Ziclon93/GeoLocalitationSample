package com.example.freenowtest.domain.usecase

import android.widget.ImageView
import com.example.freenowtest.data.images.GlideServiceImp
import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.domain.model.Location
import com.example.freenowtest.domain.model.Orientation
import com.example.freenowtest.domain.model.Vehicle
import javax.inject.Inject

class LoadVehicleResourcesUseCase @Inject constructor(
    private val glideServiceImp: GlideServiceImp,
){
    fun showVehicleImage(imageView: ImageView, vehicle: Vehicle) =
        glideServiceImp.showVehicleImage(imageView, vehicle)

}