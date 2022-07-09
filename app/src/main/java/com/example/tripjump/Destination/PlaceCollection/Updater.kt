package com.example.tripjump.Destination.PlaceCollection

import android.util.Log
import com.example.tripjump.Destination.Search.IClearPlace
import com.example.tripjump.Destination.Search.IProvidePlace
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class Updater @Inject constructor(private val data: Data): IProvidePlace, IClearPlace {
    private val TAG = Updater::class.simpleName.toString()
    override fun NewPlace(markerOptions: MarkerOptions) {
        Log.d(TAG, "Add marker")
        val destination = Destination(markerOptions)
        data.AddNewPlace(destination)
    }
}