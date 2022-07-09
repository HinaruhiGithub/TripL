package com.example.tripjump.Destination.PlaceCollection

import android.util.Log
import com.example.tripjump.System.Notification
import com.example.tripjump.System.ObserveList
import com.example.tripjump.System.Operation
import com.google.android.gms.maps.model.MarkerOptions

class Data {
    val TAG = Data::class.simpleName
    var markerRepository = ObserveList<Destination>()
    public val markerObservable = markerRepository.getObservable()

    constructor(){
        markerRepository.getObservable()
            .subscribe{ n ->
                if(n.operation == Operation.ADD) {
                    Log.d(TAG, "Add Event")
                }
        }
    }

    /**
     * 新しい奴を追加する。
     */
    fun AddNewPlace(destination: Destination) {
        markerRepository.add(destination)
    }
}