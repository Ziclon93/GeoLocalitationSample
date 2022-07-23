package com.example.freenowtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (
    val address: String,
    val city: String,
    val state: String,
    val country: String,
): Parcelable