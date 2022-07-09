package com.example.tripjump.Destination.PlaceCollection

import com.example.tripjump.MainActivity

class View {
    fun AddDestination(destination: Destination) {
        MainActivity.getInstance().AddNewPeg(destination)
    }
}