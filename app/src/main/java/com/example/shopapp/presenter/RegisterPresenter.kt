package com.example.shopapp.presenter

import com.example.shopapp.model.RegistrationHelper
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.RegisterView

class RegisterPresenter(var registerView: RegisterView?, val registerInteractor: RegisterInteractor) : RegisterInteractor.OnRegisterFinishListener {

    lateinit var registrationHelper: RegistrationHelper

    override fun onSuccess(fName: String, password: String, mobile: String, email: String) {
        registrationHelper = RegistrationHelper(ContextUtil.getRegisterContext())
        registrationHelper.registerUser(fName, password, mobile, email)
        registerView?.hideProgress()
        registerView?.navigateToLogin()
    }

    fun onDestroy() {
        registerView = null
    }

    fun register(fName: String, password: String, mobile: String, email: String) {
        registerView?.showProgress()
        registerInteractor.register(fName, password, mobile, email, this)
    }
}