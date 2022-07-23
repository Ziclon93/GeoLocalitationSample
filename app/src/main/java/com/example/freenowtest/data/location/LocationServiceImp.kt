package com.example.freenowtest.data.location

import android.location.Address
import android.location.Geocoder
import com.example.freenowtest.domain.location.LocationService
import com.example.freenowtest.domain.model.Location
import com.example.freenowtest.domain.model.Orientation

class LocationServiceImp(
    private val geocoder: Geocoder
) : LocationService {

    override fun getLocation(latitude: Double, longitude: Double): Location? {
        val addresses: List<Address?> = geocoder.getFromLocation(
            latitude,
            longitude,
            1,
        )
        if (addresses.isNotEmpty()) addresses.first()?.let {
            return Location(
                it.getAddressLine(0),
                it.locality,
                it.adminArea,
                it.countryName,
            )
        }
        return null
    }

    override fun getOrientation(heading: Float): Orientation? {
        if (heading in 0.0..30.0 || heading in 330.0..360.0) return Orientation.NORTH
        else if (heading in 30.0..60.0) return Orientation.NORTH_EAST
        else if (heading in 60.0..120.0) return Orientation.EAST
        else if (heading in 120.0..150.0) return Orientation.SOUTH_EAST
        else if (heading in 150.0..210.0) return Orientation.SOUTH
        else if (heading in 210.0..240.0) return Orientation.SOUTH_WEST
        else if (heading in 240.0..300.0) return Orientation.WEST
        else if (heading in 300.0..330.0) return Orientation.NORTH_WEST
        return null
    }
}