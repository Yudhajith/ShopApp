package com.example.shopapp.presenter

class RegisterInteractor {
    interface OnRegisterFinishListener {
        fun onSuccess(fName: String, password: String, mobile: String, email: String)
    }

    fun register(fName: String, password: String, mobile: String, email: String, listener: OnRegisterFinishListener) {
        listener.onSuccess(fName, password, mobile, email)
    }
}