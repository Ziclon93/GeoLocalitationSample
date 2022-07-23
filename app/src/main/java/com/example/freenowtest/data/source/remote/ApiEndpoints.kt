package com.example.freenowtest.data.source.remote

import com.example.freenowtest.data.source.remote.response.VehicleCatalogResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints {
    @GET(".")
    fun getVehicles(
        @Query( "p1Lat") p1Lat : Double,
        @Query( "p1Lon") p1Lon : Double,
        @Query( "p2Lat") p2Lat : Double,
        @Query( "p2Lon") p2Lon : Double,
    ): Single<VehicleCatalogResponse>
}