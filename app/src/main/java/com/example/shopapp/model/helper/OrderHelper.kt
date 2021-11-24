package com.example.shopapp.model.helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.shopapp.model.data.OrderInfo
import com.example.shopapp.view.CartView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.json.JSONObject

class OrderHelper(private val context: Context, val cartView: CartView) {

    lateinit var queue: RequestQueue

    fun placeOrder(orderInfo: OrderInfo) {
        queue = Volley.newRequestQueue(context)
        val url = "https://grocery-second-app.herokuapp.com/api/orders"

        val mapper = jacksonObjectMapper()
        val params = JSONObject(mapper.writeValueAsString(orderInfo))

//        params.put("shippingAddress", orderInfo.shippingAddress.toString())
//        params.put("payment", orderInfo.payment.toString())
//        params.put("userId", orderInfo.userId.toString())
//        params.put("products", orderInfo.products.toString())
//        params.put("orderSummary", orderInfo.orderSummary.toString())

        Log.d("OrderHelper", "placeOrder: ${params.toString()}")

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            params,
            {
                val error = it.getBoolean("error")
                val message = it.getString("message")

                cartView.onSuccess(error, message)
            },
            {
                error ->
                Toast.makeText(context, "Error occurred: ${error.toString()}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }
}