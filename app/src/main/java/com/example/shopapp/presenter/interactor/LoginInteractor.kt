package com.example.shopapp.presenter.interactor

class LoginInteractor {
    interface OnLoginFinishListener {
        fun onSuccess(email: String, password: String)
    }

    fun login(email: String, password: String, listener: OnLoginFinishListener) {
        listener.onSuccess(email, password)
    }
}