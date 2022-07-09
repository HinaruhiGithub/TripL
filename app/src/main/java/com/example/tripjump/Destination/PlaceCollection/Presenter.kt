package com.example.tripjump.Destination.PlaceCollection

import javax.inject.Inject

class Presenter @Inject constructor(val view: View, private val observe: IObserveAddData){
    init {
        observe.onAddListner = view::AddDestination
    }
}