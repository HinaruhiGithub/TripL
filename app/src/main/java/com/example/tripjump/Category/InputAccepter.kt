package com.example.tripjump.Category

import android.os.Handler
import android.os.Looper
import com.example.tripjump.MainActivity
import javax.inject.Inject

class InputAccepter @Inject constructor(private val usecase: Usecase, private val collector: IGetURLStrFromViewCategory) {
    val handler = Handler(Looper.getMainLooper())
    init {
        //強引だわ
        handler.postDelayed({
            MainActivity.getInstance().ProvideCategoryCallback(this::SelectCategory)
        }, 200L)
    }

    fun SelectCategory(viewCategory: IGetViewCategory){
        val category = collector.GetURLStrFromViewCategory(viewCategory)
        usecase.DecideCategory(category)
    }
}