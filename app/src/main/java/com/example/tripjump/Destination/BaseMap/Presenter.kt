package com.example.tripjump.Destination.BaseMap

import android.util.Log
import com.example.tripjump.Destination.Search.Provider
import javax.inject.Inject


class Presenter @Inject constructor(private val provider: Provider, private val view: View){
    val TAG = Presenter::class.simpleName
    init{
        Log.d(TAG, "I Called!!")
        provider.showListener = view::ShowMap
    }
}