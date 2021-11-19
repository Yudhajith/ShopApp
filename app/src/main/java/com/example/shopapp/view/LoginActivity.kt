package com.example.shopapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var registerFragment: RegisterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerFragment = RegisterFragment()

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
        binding.root.removeAllViews()
        supportFragmentManager.beginTransaction().replace(binding.container.id, registerFragment).commit()
    }

    private fun login() {

    }

}