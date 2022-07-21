package com.example.tripjump.Category

import com.example.tripjump.Destination.Search.FinishWaiting
import javax.inject.Inject

class Usecase @Inject constructor(private val transition: ITransitionFromCategory) {
    var showCategoryListner: (() -> Unit)? = null
    var hideCategoryListner: (() -> Unit)? = null

    fun StartSelectCategory(){
        showCategoryListner?.invoke()
    }

    /*
    カテゴリを決定して、次の奴に移行する。
     */
    fun DecideCategory(category: IGetURLStringCategory){
        transition.TransitionToCategory(category)
        hideCategoryListner?.invoke()
    }
}