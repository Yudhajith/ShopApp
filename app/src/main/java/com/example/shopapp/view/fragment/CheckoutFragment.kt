package com.example.shopapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.databinding.FragmentCheckoutBinding
import com.example.shopapp.model.DatabaseHandler
import com.example.shopapp.model.adapters.CheckoutAdapter
import com.example.shopapp.model.data.OrderInfo
import com.example.shopapp.model.helper.OrderHelper
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.CartView
import com.example.shopapp.view.activity.HomeActivity

class CheckoutFragment(val orderInfo: OrderInfo) : Fragment(), CartView {

    lateinit var binding: FragmentCheckoutBinding
    private lateinit var orderHelper: OrderHelper
    lateinit var databaseHandler: DatabaseHandler
    lateinit var communicator: Communicator
    lateinit var adapter: CheckoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        orderHelper = OrderHelper(ContextUtil.getHomeContext(), this)
        databaseHandler = DatabaseHandler(ContextUtil.getHomeContext())
        communicator = activity as Communicator

        adapter = CheckoutAdapter(orderInfo.products)

        binding.rvShoppingCart.layoutManager = LinearLayoutManager(activity)

        binding.rvShoppingCart.adapter = adapter

        setInfo()


        binding.btnCheckout.setOnClickListener {
            orderHelper.placeOrder(orderInfo)

            navigateToHome()
        }

        binding.btnBack.setOnClickListener {
            communicator.toCartPage()
        }

        return binding.root
    }

    private fun setInfo() {
        binding.apply {
            val subtotal = orderInfo.orderSummary.totalAmount
            val tax = subtotal * 0.1
            val total = subtotal + tax
            tvSubtotal.text = "Subtotal: ${subtotal.toString()}"
            tvTax.text =  "Taxes: ${tax.toString()}"
            tvTotal.text = "Total: ${total.toString()}"

            tvStreet.text = "Street: ${orderInfo.shippingAddress.streetName}"
            tvHouse.text = "House No: ${orderInfo.shippingAddress.houseNo}"
            tvPincode.text = "Pincode: ${orderInfo.shippingAddress.pincode}"
            tvCity.text = "City: ${orderInfo.shippingAddress.city}"
        }
    }

    private fun navigateToHome() {
        startActivity(Intent(ContextUtil.getHomeContext(), HomeActivity::class.java))
    }

    override fun onSuccess(error: Boolean, message: String) {
        if (error) {
            Toast.makeText(context, "Failed to place order: $message", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            databaseHandler.emptyCart()
            navigateToHome()
        }
    }
}