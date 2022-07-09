package com.example.tripjump.Destination.Search

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class Provider @Inject constructor(private val updater: IProvidePlace) {
    private val firstPlace: LatLng = LatLng(35.toDouble(), 135.toDouble())
    private val TAG = Provider::class.simpleName.toString()
    var showListener: (() -> Unit)? = null

    fun StartSearching(origin: Location) {
        Log.d(TAG, origin.latitude.toString())
        if(showListener == null) {
            Log.e(TAG, "ねぇわ")
        } else {
            Log.d(TAG, "あったわ")
            TestProvide()
            showListener?.invoke()
        }
    }

    private fun TestProvide() {
        val osakaStation = MarkerOptions()
            .title("大阪駅")
            .snippet("JRやで")
            .position(LatLng(34.702485, 135.495951))
        updater.NewPlace(osakaStation)
    }
}