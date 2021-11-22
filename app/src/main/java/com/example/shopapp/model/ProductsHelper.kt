package com.example.shopapp.model

import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.ProductView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsHelper(val productView: ProductView) {

    lateinit var requestQueue: RequestQueue
    lateinit var products: List<Product>

    fun getProducts(subcategoryID: Int) {
        val context = ContextUtil.getProductContext()
        requestQueue = Volley.newRequestQueue(context)
        products = mutableListOf()

        val url = "https://grocery-second-app.herokuapp.com/api/products/sub/$subcategoryID"

        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                response ->

                val gson = Gson()
                val typeToken = object : TypeToken<ProductsInfo>(){}
                val productsInfo = gson.fromJson<ProductsInfo>(response, typeToken.type)

                Log.d("ProductsHelper", "getProducts: ${productsInfo.toString()}")

                if (!productsInfo.error) {
                    products = productsInfo.data
                    productView.onSuccess(products)
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