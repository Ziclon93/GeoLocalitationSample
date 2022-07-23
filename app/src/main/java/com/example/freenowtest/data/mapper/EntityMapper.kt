package com.example.freenowtest.data.mapper

import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.data.source.local.entity.VehicleEntity
import com.example.freenowtest.domain.model.*

fun List<VehicleEntity>.toVehicleCatalog(locationService: LocationServiceImp): List<Vehicle> =
    this.map {
        it.toVehicle(
            locationService.getLocation(
                it.latitude.toDouble(), it.longitude.toDouble()
            ),
            locationService.getOrientation(it.heading),
        )
    }

fun VehicleEntity.toVehicle(location: Location?, orientation: Orientation?): Vehicle = Vehicle(
    id,
    Coordinate(latitude, longitude),
    location,
    FleetType.valueOf(fleetType),
    heading,
    orientation,
)

fun List<Vehicle>.toEntity(): List<VehicleEntity> = this.map { it.toEntity() }

fun Vehicle.toEntity(): VehicleEntity = VehicleEntity(
    id,
    coordinate.latitude,
    coordinate.longitude,
    fleetType.name,
    heading,
)
