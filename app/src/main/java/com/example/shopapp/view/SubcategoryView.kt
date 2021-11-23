package com.example.shopapp.view

import com.example.shopapp.model.data.SubCategory

interface SubcategoryView {

    fun onSuccess(subcategories: List<SubCategory>)
}