package com.example.shopapp.model.helper

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shopapp.model.data.CategoriesInfo
import com.example.shopapp.model.data.Data
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.HomeView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoriesHelper(val homeView: HomeView) {

    lateinit var requestQueue: RequestQueue
    lateinit var categories: List<Data>

    fun getCategories() {
        val context = ContextUtil.getHomeContext()
        requestQueue = Volley.newRequestQueue(context)
        categories = mutableListOf()

        val url = "https://grocery-second-app.herokuapp.com/api/category"

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                response ->
                val gson = Gson()
                val typeToken = object : TypeToken<CategoriesInfo>(){}
                val categoriesInfo = gson.fromJson<CategoriesInfo>(response, typeToken.type)

                if (!categoriesInfo.error) {
                    categories = categoriesInfo.data
                    homeView.onSuccess(categories)
                }

            },
            {
                Toast.makeText(context, "Error occurred: ${it.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        )
        requestQueue.add(request)
    }
}