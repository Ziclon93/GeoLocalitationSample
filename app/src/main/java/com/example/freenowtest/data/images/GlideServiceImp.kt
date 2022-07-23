package com.example.freenowtest.data.images

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.freenowtest.R
import com.example.freenowtest.domain.images.GlideService
import com.example.freenowtest.domain.model.FleetType
import com.example.freenowtest.domain.model.Vehicle

class GlideServiceImp(
    private val context: Context,
) : GlideService {

    override fun showVehicleImage(imageView: ImageView, vehicle: Vehicle) {
        Glide.with(context).load(
            if (vehicle.fleetType == FleetType.TAXI) R.drawable.ic_vehicle_taxi else R.drawable.ic_vehicle,
        ).into(imageView)
    }
}