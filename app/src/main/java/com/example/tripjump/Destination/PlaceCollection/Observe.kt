package com.example.tripjump.Destination.PlaceCollection

import com.example.tripjump.Destination.Search.IObservePlaceAppear
import com.example.tripjump.System.Operation
import javax.inject.Inject

class Observe @Inject constructor(private val data: Data): IObserveAddData, IObservePlaceAppear {

    override var onAddListner: ((Destination) -> Unit)? = null

    init {
        data.markerObservable
            .filter{x -> x.operation == Operation.ADD}
            .subscribe{ destination ->
                onAddListner?.invoke(destination.Item) ?: run {
                    throw NullPointerException("still not set onAddListner")
                }
            }
    }
}