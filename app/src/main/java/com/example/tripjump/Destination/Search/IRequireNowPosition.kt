package com.example.tripjump.Destination.Search

import android.location.Location
import com.google.android.gms.maps.model.LatLng

interface IRequireNowPosition {
    suspend fun RequireNowPosition(): Location
    fun JustCallPosition() : Location
}