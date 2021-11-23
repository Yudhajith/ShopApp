package com.example.shopapp.presenter

import com.example.shopapp.model.helper.LoginHelper
import com.example.shopapp.presenter.interactor.LoginInteractor
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.LoginView

class LoginPresenter(var loginView: LoginView?, val loginInteractor: LoginInteractor) : LoginInteractor.OnLoginFinishListener {

    lateinit var loginHelper: LoginHelper

    override fun onSuccess(email: String, password: String) {
        loginHelper = LoginHelper(ContextUtil.getLoginContext())
        loginHelper.login(email, password)
        loginView?.hideProgress()
        loginView?.navigateToHome()
    }

    fun onDestroy() {
        loginView = null
    }

    fun login(email: String, password: String) {
        loginView?.showProgress()
        loginInteractor.login(email, password, this)
    }
}