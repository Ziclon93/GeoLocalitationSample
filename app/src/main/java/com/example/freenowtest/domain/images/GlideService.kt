package com.example.freenowtest.domain.images

import android.widget.ImageView
import com.example.freenowtest.domain.model.Vehicle

interface GlideService {
    fun showVehicleImage(imageView: ImageView, vehicle: Vehicle)
}