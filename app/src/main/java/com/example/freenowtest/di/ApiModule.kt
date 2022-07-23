package com.example.freenowtest.di

import android.content.Context
import com.example.freenowtest.data.location.LocationServiceImp
import com.example.freenowtest.data.source.remote.ApiEndpoints
import com.example.freenowtest.data.repository.VehicleCatalogRepositoryImp
import com.example.freenowtest.data.source.local.AppDatabase
import com.example.freenowtest.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideVehicleCatalogRepository(
        endpoints: ApiEndpoints,
        appDatabase: AppDatabase,
        locationService: LocationServiceImp,
    ) = VehicleCatalogRepositoryImp(
        endpoints,
        appDatabase,
        locationService
    )

    @Provides
    @Singleton
    fun provideApiEndpoints(
        retrofit: Retrofit
    ): ApiEndpoints = retrofit.create()


    @Provides
    @Singleton
    fun provideRetrofit(
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        client: OkHttpClient,
    ): Retrofit = with(Retrofit.Builder()) {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        addCallAdapterFactory(rxJava2CallAdapterFactory)
        client(client)
        build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient = with(OkHttpClient.Builder()) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(logging)
        cache(Cache(context.cacheDir, (5 * 1024 * 1024).toLong()))
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        pingInterval(30, TimeUnit.SECONDS)
        build()
    }


    @Provides
    @Singleton
    fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

}