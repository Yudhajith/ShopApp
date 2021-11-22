package com.example.shopapp.view

import com.example.shopapp.model.Product

interface ProductView {

    fun onSuccess(products: List<Product>)
}