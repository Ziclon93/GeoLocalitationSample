package com.example.freenowtest.data.mapper

import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.data.source.remote.response.VehicleCatalogResponse
import com.example.freenowtest.data.source.remote.response.VehicleResponse
import com.example.freenowtest.domain.model.*

fun VehicleCatalogResponse.toVehicleList(locationService: LocationServiceImp): List<Vehicle> =
    this.poiList.map {
        it.toVehicle(
            locationService.getLocation(
                it.coordinate.latitude.toDouble(),
                it.coordinate.longitude.toDouble()
            ),
            locationService.getOrientation(it.heading),
        )
    }

fun VehicleResponse.toVehicle(location: Location?, orientation: Orientation?): Vehicle = Vehicle(
    id,
    Coordinate(coordinate.latitude, coordinate.longitude),
    location,
    FleetType.valueOf(fleetType),
    heading,
    orientation,
)
