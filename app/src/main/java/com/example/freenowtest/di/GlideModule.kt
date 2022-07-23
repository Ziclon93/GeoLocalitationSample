package com.example.freenowtest.di

import android.content.Context
import com.example.freenowtest.data.images.GlideServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlideModule {
    @Provides
    @Singleton
    fun provideGlideService(
        @ApplicationContext context: Context,
    ) = GlideServiceImp(context)

}