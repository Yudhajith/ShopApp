package com.example.shopapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener {
                login()
            }

            btnRegister.setOnClickListener {
                register()
            }
        }
    }

    private fun register() {

    }

    private fun login() {
        TODO("Not yet implemented")
    }

}