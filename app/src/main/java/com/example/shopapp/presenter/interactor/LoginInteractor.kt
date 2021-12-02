package com.example.shopapp.presenter.interactor

class LoginInteractor {
    interface OnLoginFinishListener {
        fun onSuccess()
        fun onFailure()
    }
}