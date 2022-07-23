package com.example.freenowtest.ui.catalog.adapter

import android.widget.ImageView
import com.example.freenowtest.domain.model.Vehicle

interface OnVehicleCatalogAdapterListener {
    fun showVehicleInMap(vehicle: Vehicle)
    fun showViewHolder(vehicleImg: ImageView, vehicle: Vehicle)
}
