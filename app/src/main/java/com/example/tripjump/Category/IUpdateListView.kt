package com.example.tripjump.Category

interface IUpdateListView {
    fun StartShowList()
    fun AddCategory(newCategory: List<IGetViewCategory>)
    fun FinishShowList()
}