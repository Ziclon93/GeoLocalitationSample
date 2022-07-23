package com.example.freenowtest.domain.location

import com.example.freenowtest.domain.model.Location
import com.example.freenowtest.domain.model.Orientation
import com.example.freenowtest.domain.model.Vehicle

interface LocationService {
    fun getLocation(latitude: Double, longitude: Double): Location?
    fun getOrientation(heading: Float): Orientation?
}