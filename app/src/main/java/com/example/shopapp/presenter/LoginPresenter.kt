package com.example.shopapp.presenter

import android.widget.Toast
import com.example.shopapp.model.helper.LoginHelper
import com.example.shopapp.presenter.interactor.LoginInteractor
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.LoginView

class LoginPresenter(var loginView: LoginView?, private val loginInteractor: LoginInteractor) : LoginInteractor.OnLoginFinishListener {

    lateinit var loginHelper: LoginHelper

    override fun onSuccess() {
        loginView?.hideProgress()
        loginView?.navigateToHome()
    }

    override fun onFailure() {
        loginView?.hideProgress()
        loginView?.loginFailed()
    }

    fun onDestroy() {
        loginView = null
    }

    fun login(email: String, password: String) {
        loginView?.showProgress()
        loginHelper = LoginHelper(ContextUtil.getLoginContext())
        loginHelper.login(email, password, this)
    }
}