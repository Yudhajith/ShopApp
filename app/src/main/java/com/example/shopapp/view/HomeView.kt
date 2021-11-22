package com.example.shopapp.view

import com.example.shopapp.model.Data

interface HomeView {
    fun onSuccess(categories: List<Data>)
}