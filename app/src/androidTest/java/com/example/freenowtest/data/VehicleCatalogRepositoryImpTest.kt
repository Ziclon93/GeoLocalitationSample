package com.example.freenowtest.data

import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.data.repository.VehicleCatalogRepositoryImp
import com.example.freenowtest.data.source.local.AppDatabase
import com.example.freenowtest.data.source.remote.ApiEndpoints
import com.example.freenowtest.domain.model.*
import com.example.freenowtest.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class VehicleCatalogRepositoryImpTest {


    private lateinit var mDatabase: AppDatabase
    private lateinit var mEndpoints: ApiEndpoints
    private lateinit var mGeocoder:Geocoder
    private lateinit var mVehicleLocationImp: VehicleCatalogRepositoryImp

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        mEndpoints = with(Retrofit.Builder()) {
            baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(with(OkHttpClient.Builder()) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logging)
                    connectTimeout(30, TimeUnit.SECONDS)
                    readTimeout(30, TimeUnit.SECONDS)
                    writeTimeout(30, TimeUnit.SECONDS)
                    pingInterval(30, TimeUnit.SECONDS)
                    build()
                }).build()
        }.create()

        mDatabase = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()
        mGeocoder = Geocoder(context)

        mVehicleLocationImp = VehicleCatalogRepositoryImp(mEndpoints, mDatabase, LocationServiceImp(mGeocoder))
    }

    @Test
    fun readEmptyListDao() {
        val catalog = mVehicleLocationImp.getVehicleCatalog()

        assert(catalog == emptyList<Vehicle>())
    }


    @Test
    fun requestVehicleCatalog() {
        mVehicleLocationImp.requestVehicleCatalog().test().assertComplete().assertValueCount(1)
    }

    @Test
    fun writeAndReadListDao() {
        val expectedVehicleList = listOf(
            Vehicle(
                1,
                Coordinate(32.4f, 35.3f),
                null,
                FleetType.TAXI,
                1f,
                Orientation.NORTH,
            )
        )

        mVehicleLocationImp.putVehicleCatalog(expectedVehicleList)
        val catalog = mVehicleLocationImp.getVehicleCatalog()

        assert(catalog == expectedVehicleList)
    }

}