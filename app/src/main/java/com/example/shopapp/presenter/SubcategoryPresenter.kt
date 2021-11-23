package com.example.shopapp.presenter

import com.example.shopapp.model.helper.SubcategoriesHelper
import com.example.shopapp.view.SubcategoryView

class SubcategoryPresenter(val subcategoryView: SubcategoryView) {

    lateinit var subcategoriesHelper: SubcategoriesHelper

    fun getSubcategories(categoryID: Int) {
        subcategoriesHelper = SubcategoriesHelper(subcategoryView)

        subcategoriesHelper.getSubcategories(categoryID)
    }
}