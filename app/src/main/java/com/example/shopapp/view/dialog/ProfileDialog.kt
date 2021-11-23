package com.example.shopapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup
import com.example.shopapp.databinding.DialogProfileBinding

class ProfileDialog(context: Context, private val sharedPreferences: SharedPreferences) : Dialog(context) {

    lateinit var binding: DialogProfileBinding

    private lateinit var onSuccess: () -> Unit
    private lateinit var onCancel: () -> Unit
    private lateinit var onFailure: () -> Unit

    fun setOnSuccessListener(listener: () -> Unit) {
        this.onSuccess = listener
    }

    fun setOnCancelListener(listener: () -> Unit) {
        this.onCancel = listener
    }

    fun setOnFailureListener(listener: () -> Unit) {
        this.onFailure = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogProfileBinding.inflate(layoutInflater)

        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val mobile = sharedPreferences.getString("mobile", "")

        binding.apply {
            tvFName.text = name
            tvEmail.text = email
            tvMobile.text = mobile
        }

        setContentView(binding.root)

        binding.btnExit.setOnClickListener {
            exit()
        }

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun exit() {
        onSuccess()
    }
}