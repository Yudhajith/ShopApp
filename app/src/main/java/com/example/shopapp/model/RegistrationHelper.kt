package com.example.shopapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import org.json.JSONObject

class RegistrationHelper(private val context: Context) {

    lateinit var queue: RequestQueue

    fun registerUser(fName: String, password: String, mobile: String, email: String) {
        queue = Volley.newRequestQueue(context)
        val url = "https://grocery-second-app.herokuapp.com/api/auth/register"
        val params = JSONObject()
        params.put("firstName",fName)
        params.put("password", password)
        params.put("mobile", mobile)
        params.put("email", email)

        Log.d("Request", "registerUser: ${params.toString()}")

        val request1 = JsonObjectRequest(
            Request.Method.POST,
            url,
            params,
            {

                val status = it.getInt("status")
                val message = it.getString("message")

                if (status == 0) {
                    Toast.makeText(context, "Registered successfully: $message", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to register: $message", Toast.LENGTH_SHORT).show()
                }
            },
            {
                error ->
                error.printStackTrace()
                Toast.makeText(context, "Error occurred: ${error.toString()}", Toast.LENGTH_SHORT).show()
            }
        )

//        val request = object: StringRequest(
//            Method.POST,
//            url,
//            {
//                response: String ->
//                val jsonObject = JSONObject(response)
//                val status = jsonObject.getInt("status")
//                val message = jsonObject.getString("message")
//
//                if (status == 0) {
//                    Toast.makeText(context, "Registered successfully: $message", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Failed to register: $message", Toast.LENGTH_SHORT).show()
//                }
//            },
//            {
//                error ->
//                Toast.makeText(context, "Error occurred: ${error.toString()}", Toast.LENGTH_SHORT).show()
//            }
//        ) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val valueMap = mutableMapOf<String, String>()
//                valueMap.put("firstName", fName)
//                valueMap.put("mobile", mobile)
//                valueMap.put("password", password)
//                valueMap.put("email", email)
//                return valueMap
//            }
//        }

        queue.add(request1)
    }
}