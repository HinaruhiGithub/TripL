package com.example.tripjump.Category

import com.example.tripjump.MainActivity
import javax.inject.Singleton

@Singleton
class ListView: IUpdateListView {
    override fun StartShowList() {
        MainActivity.getInstance().StartShowCategoryFragment()
    }

    override fun AddCategory(newCategory: List<IGetViewCategory>) {
        MainActivity.getInstance().AddCategoryToFragment(newCategory)
    }

    override fun FinishShowList() {
        MainActivity.getInstance().FinishShowCategoryFragment()
    }
}