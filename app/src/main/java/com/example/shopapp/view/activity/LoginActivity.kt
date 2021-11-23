package com.example.shopapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.shopapp.databinding.ActivityLoginBinding
import com.example.shopapp.presenter.interactor.LoginInteractor
import com.example.shopapp.presenter.LoginPresenter
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.LoginView
import com.example.shopapp.view.fragment.RegisterFragment

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding
    lateinit var registerFragment: RegisterFragment
    lateinit var presenter: LoginPresenter

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
        ContextUtil.setLoginContext(this)
        presenter = LoginPresenter(this, LoginInteractor())
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        //navigateToHome()

        presenter.login(email, password)

    }

    override fun showProgress() {
        binding.pbProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.pbProgress.visibility = View.GONE
    }

    override fun navigateToHome() {
        startActivity(Intent(baseContext, HomeActivity::class.java))
    }

}