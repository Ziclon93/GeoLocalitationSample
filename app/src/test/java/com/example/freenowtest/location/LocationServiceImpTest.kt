package com.example.freenowtest.location

import android.location.Address
import android.location.Geocoder
import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.domain.model.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class LocationServiceImpTest {

    private lateinit var mVehicleLocationImp: LocationServiceImp
    private lateinit var mGeoCoder: Geocoder

    private val testLatitude = 41.3608065
    private val testLongitude = 2.1073213

    @Before
    fun setup() {
        mGeoCoder = mockk(relaxed = true)
        mVehicleLocationImp = LocationServiceImp(mGeoCoder)
    }

    @Test
    fun insertPhoto() {
        val mAddress = mockk<Address>()
        val expectedLocation = Location("carrer", "locality", "adminarea", "countryname")

        every {
            mGeoCoder.getFromLocation(
                testLatitude,
                testLongitude,
                1,
            )
        } returns mutableListOf(mAddress)
        every { mAddress.getAddressLine(0) } returns "carrer"
        every { mAddress.locality } returns "locality"
        every { mAddress.adminArea } returns "adminarea"
        every { mAddress.countryName } returns "countryname"

        val loc: Location? = mVehicleLocationImp.getLocation(
            testLatitude,
            testLongitude,
        )

        assert(loc == expectedLocation)
    }

    @Test
    fun insertPhotoNullCase() {
        val expectedLocation: Location? = null

        every {
            mGeoCoder.getFromLocation(
                testLatitude,
                testLongitude,
                1,
            )
        } returns listOf(null)

        val loc: Location? = mVehicleLocationImp.getLocation(
            testLatitude,
            testLongitude,
        )

        assert(loc == expectedLocation)
    }

    @Test
    fun getOrientation() {
        val testHeading = 315.1073213f
        val expectedOrientation: Orientation = Orientation.NORTH_WEST

        val orientation: Orientation? = mVehicleLocationImp.getOrientation(testHeading)

        assert(orientation == expectedOrientation)
        assert(orientation != Orientation.NORTH)
        assert(orientation != Orientation.WEST)
        assert(orientation != Orientation.SOUTH)
    }

    @Test
    fun getOrientationNullCase() {
        val testHeading = -315.1073213f

        val expectedOrientation: Orientation? = null

        val orientation: Orientation? = mVehicleLocationImp.getOrientation(testHeading)

        assert(orientation == expectedOrientation)
    }
}