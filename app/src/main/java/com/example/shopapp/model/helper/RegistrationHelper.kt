package com.example.shopapp.model.helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegistrationHelper(private val context: Context) {

    lateinit var queue: RequestQueue

    fun registerUser(fName: String, password: String, mobile: String, email: String) {
        queue = Volley.newRequestQueue(context)
        val url = "https://grocery-second-app.herokuapp.com/api/auth/register"
        val params = JSONObject()
        params.put("firstName",fName)
        params.put("mobile", mobile)
        params.put("password", password)
        params.put("email", email)

        Log.d("Request", "registerUser: ${params.toString()}")

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            params,
            {

                val error = it.getBoolean("error")
                val message = it.getString("message")

                if (error) {
                    Toast.makeText(context, "Failed to register: $message", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            },
            {
                error ->
                error.printStackTrace()
                Toast.makeText(context, "Error occurred: ${error.toString()}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }
}