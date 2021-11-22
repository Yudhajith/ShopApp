package com.example.shopapp.presenter

import com.example.shopapp.model.ProductsHelper
import com.example.shopapp.view.ProductView

class ProductPresenter(val productView: ProductView) {

    lateinit var productsHelper: ProductsHelper

    fun getProducts(subcategoryID: Int) {
        productsHelper = ProductsHelper(productView)

        productsHelper.getProducts(subcategoryID)
    }
}