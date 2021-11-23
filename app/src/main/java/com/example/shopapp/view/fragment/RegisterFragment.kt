package com.example.shopapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopapp.databinding.FragmentRegisterBinding
import com.example.shopapp.presenter.interactor.RegisterInteractor
import com.example.shopapp.presenter.RegisterPresenter
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.RegisterView
import com.example.shopapp.view.activity.LoginActivity

class RegisterFragment : Fragment(), RegisterView {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        presenter = RegisterPresenter(this, RegisterInteractor())

        binding.apply {
            btnSignup.setOnClickListener {
                val fName = etFirstName.text.toString()
                val password = etPassword.text.toString()
                val mobile = etMobile.text.toString()
                val email = etEmail.text.toString()
                register(fName, password, mobile, email)
            }

            btnLogin.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
        return binding.root
    }

    private fun register(fName: String, password: String, mobile: String, email: String) {
        ContextUtil.setRegisterContext(this.context)
        presenter.register(fName, password, mobile, email)
    }

    override fun showProgress() {
        binding.pbProgress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.pbProgress.visibility = View.VISIBLE
    }

    override fun navigateToLogin() {
        startActivity(Intent(this.context, LoginActivity::class.java))
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}