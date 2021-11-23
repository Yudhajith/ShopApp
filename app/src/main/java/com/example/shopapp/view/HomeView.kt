package com.example.shopapp.view

import com.example.shopapp.model.data.Data

interface HomeView {
    fun onSuccess(categories: List<Data>)
}