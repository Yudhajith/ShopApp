package com.example.shopapp.presenter

interface Communicator {

    fun toProductsPage(subcategoryID: Int, categoryID: Int, subcategoryName: String)
}