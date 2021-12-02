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
import com.example.shopapp.presenter.interactor.LoginInteractor.OnLoginFinishListener


class LoginHelper(private val context: Context) {

    lateinit var requestQueue: RequestQueue

    fun login(email: String, password: String, listener: OnLoginFinishListener) {
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
                val user = gson.fromJson<User>(it.getString("user").toString(), typeToken.type)

                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                editor.putString("userid", user._id)
                editor.putString("name", user.firstName)
                editor.putString("email", user.email)
                editor.putString("mobile", user.mobile)
                editor.putBoolean("isLoggedIn", true)

                editor.apply()
                listener.onSuccess()
            },
            {
                    error ->
                Log.e("Login", "login: ${error.message}")
                listener.onFailure()
            },

        )
        requestQueue.add(request)
    }
}