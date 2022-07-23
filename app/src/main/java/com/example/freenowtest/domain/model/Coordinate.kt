package com.example.freenowtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate (
    val latitude: Float,
    val longitude: Float,
) : Parcelable