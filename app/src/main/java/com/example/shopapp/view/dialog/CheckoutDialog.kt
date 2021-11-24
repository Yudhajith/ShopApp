package com.example.shopapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.shopapp.databinding.DialogCheckoutBinding
import com.example.shopapp.model.data.ShippingAddress

class CheckoutDialog(context: Context) : Dialog(context) {

    lateinit var binding: DialogCheckoutBinding

    private lateinit var onSuccess: (shippingAddress: ShippingAddress) -> Unit
    private lateinit var onCancel: () -> Unit
    private lateinit var onFailure: () -> Unit

    fun setOnSuccessListener(listener: (shippingAddress: ShippingAddress) -> Unit) {
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
        binding = DialogCheckoutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnCheckout.setOnClickListener {
            placeOrder()
        }

        binding.btnCancel.setOnClickListener {
            onCancel()
        }

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun placeOrder() {
        val streetName = binding.etStreet.text.toString()
        val houseNo = binding.etHouseNo.text.toString()
        val pincode = binding.etPincode.text.toString().toInt()
        val city = binding.etCity.text.toString()

        binding.pbCheckout.visibility = View.VISIBLE

        onSuccess(ShippingAddress(city, houseNo, pincode, streetName, city))
    }
}