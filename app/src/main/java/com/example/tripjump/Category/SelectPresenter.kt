package com.example.tripjump.Category

import javax.inject.Inject

class SelectPresenter @Inject constructor(private val viewCategory: IGetViewList, private val updateView: IUpdateListView, private val usecase: Usecase){
    init {
        usecase.showCategoryListner = this::ShowAllCategory
        usecase.hideCategoryListner = this::HideCategoryFragment
    }


    private fun ShowAllCategory() {
        val allCategorys = viewCategory.GetAllCategoryView()
//        updateView.StartShowList()
        updateView.AddCategory(allCategorys)
    }

    private fun HideCategoryFragment() {
        updateView.FinishShowList()
    }
}