package com.example.shopapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.example.shopapp.databinding.DialogAddToCartBinding

class AddToCartDialog(context: Context) : Dialog(context) {

    lateinit var binding: DialogAddToCartBinding

    private lateinit var onSuccess: (amount: Int) -> Unit
    private lateinit var onCancel: () -> Unit
    private lateinit var onFailure: () -> Unit

    fun setOnSuccessListener(listener: (amount: Int) -> Unit) {
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
        binding = DialogAddToCartBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnAddToCart.setOnClickListener {
            addItemToCart()
        }

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun addItemToCart() {
        val etAmount = binding.etAmount.text.toString()
        if ((etAmount.toInt()) > 0) {
            val amount = etAmount.toInt()
            onSuccess(amount)
        }
    }
}