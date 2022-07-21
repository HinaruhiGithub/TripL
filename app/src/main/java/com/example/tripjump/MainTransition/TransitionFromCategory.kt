package com.example.tripjump.MainTransition

import com.example.tripjump.Category.IGetURLStringCategory
import com.example.tripjump.Category.ITransitionFromCategory
import com.example.tripjump.Destination.Search.RequestAcception
import javax.inject.Inject

class TransitionFromCategory @Inject constructor(private val requestAcception: RequestAcception): ITransitionFromCategory {
    override fun TransitionToCategory(category: IGetURLStringCategory) {
        requestAcception.RequestAccept(category)
    }
}