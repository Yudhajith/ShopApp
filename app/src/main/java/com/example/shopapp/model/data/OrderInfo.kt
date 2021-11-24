package com.example.shopapp.model.data

data class OrderInfo(
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<Item>,
    val shippingAddress: ShippingAddress,
    val userId: String
)

data class OrderSummary(
    val deliveryCharges: Int,
    val discount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment(
    val paymentMode: String
)

data class Item(
    val id: String,
    val price: Int,
    val productName: String,
    val quantity: Int
)

data class ShippingAddress(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)