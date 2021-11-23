package com.example.shopapp.model.helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.shopapp.model.data.User


class LoginHelper(private val context: Context) {

    lateinit var requestQueue: RequestQueue

    fun login(email: String, password: String) {
        requestQueue = Volley.newRequestQueue(context)

        val sharedPreferences = context.getSharedPreferences("ShopApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val url = "https://grocery-second-app.herokuapp.com/api/auth/login"
        val params = JSONObject()
        params.put("email", email)
        params.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            params,
            {
                val typeToken = object : TypeToken<User>() {}
                val gson = Gson()
                val user = gson.fromJson<User>(it.toString(), typeToken.type)

                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                Log.i("Login", "login: Success")
                editor.putString("name", user.firstName)
                editor.putString("email", user.email)
                editor.putString("mobile", user.mobile)
                editor.putBoolean("isLoggedIn", true)

                editor.apply()
            },
            {
                    error ->
                Toast.makeText(context, "Error occurred: ${error.toString()}", Toast.LENGTH_SHORT).show()
                Log.e("Login", "login: ${error.message}")
            },

        )
        requestQueue.add(request)
    }
}