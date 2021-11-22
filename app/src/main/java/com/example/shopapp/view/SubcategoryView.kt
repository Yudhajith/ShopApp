package com.example.shopapp.view

import com.example.shopapp.model.SubCategory

interface SubcategoryView {

    fun onSuccess(subcategories: List<SubCategory>)
}