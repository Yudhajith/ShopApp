package com.example.shopapp.model

data class SubcategoriesInfo(
    val count: Int,
    val data: List<SubCategory>,
    val error: Boolean
)

data class SubCategory(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
)