package com.example.shopapp.presenter

import com.example.shopapp.model.CategoriesAdapter
import com.example.shopapp.model.CategoriesHelper
import com.example.shopapp.view.HomeView

class HomePresenter(val homeView: HomeView) {

    lateinit var categoriesHelper: CategoriesHelper
    fun loadCategories() {
        categoriesHelper = CategoriesHelper(homeView)

        return categoriesHelper.getCategories()
    }
}