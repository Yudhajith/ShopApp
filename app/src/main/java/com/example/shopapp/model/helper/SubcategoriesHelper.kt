package com.example.shopapp.model.helper

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shopapp.model.data.SubCategory
import com.example.shopapp.model.data.SubcategoriesInfo
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.SubcategoryView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubcategoriesHelper(val subcategoryView: SubcategoryView) {

    lateinit var requestQueue: RequestQueue
    lateinit var subcategories: List<SubCategory>

    fun getSubcategories(categoryID: Int) {
        val context = ContextUtil.getSubcategoryContext()
        requestQueue = Volley.newRequestQueue(context)
        subcategories = mutableListOf()

        val url = "https://grocery-second-app.herokuapp.com/api/subcategory/$categoryID"

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                response ->

                val gson = Gson()
                val typeToken = object : TypeToken<SubcategoriesInfo>(){}
                val subcategoriesInfo = gson.fromJson<SubcategoriesInfo>(response, typeToken.type)

                if (!subcategoriesInfo.error) {
                    subcategories = subcategoriesInfo.data
                    subcategoryView.onSuccess(subcategories)
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