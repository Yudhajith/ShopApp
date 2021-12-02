package com.example.shopapp.presenter

import com.example.shopapp.model.data.OrderInfo

interface Communicator {

    fun toProductsPage(subcategoryID: Int, categoryID: Int, subcategoryName: String, catImage: String)

    fun toCheckoutPage(orderInfo: OrderInfo)

    fun toCartPage()

    fun toSubCatPage(subcategoryID: Int, subcategoryName: String, subcategoryImage: String)
}