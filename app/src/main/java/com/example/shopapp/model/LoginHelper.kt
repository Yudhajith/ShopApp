package com.example.shopapp.model

import android.content.Context
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginHelper(private val context: Context) {

    lateinit var requestQueue: RequestQueue

    fun login(email: String, password: String) {
        requestQueue = Volley.newRequestQueue(context)
        val url = "https://grocery-second-app.herokuapp.com/api/auth/login"
        val sharedPreferences = context.getSharedPreferences("ShopApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val request = object: StringRequest(
            Method.POST,
            url,
            {
                    response: String ->
                val typeToken = object : TypeToken<UserModel>() {}
                val gson = Gson()
                val user = gson.fromJson<UserModel>(response, typeToken.type)

                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()


                editor.putString("name", user.user.firstName)
                editor.putString("email", user.user.email)
                editor.putString("mobile", user.user.mobile)
                editor.putBoolean("isLoggedIn", true)

                editor.apply()
            },
            {
                    error ->
                Toast.makeText(context, "Error occurred: ${error.toString()}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val valueMap = mutableMapOf<String, String>()
                valueMap.put("password", password)
                valueMap.put("email", email)
                return valueMap
            }
        }

        requestQueue.add(request)
    }
}