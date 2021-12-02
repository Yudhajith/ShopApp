package com.example.shopapp.view.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.databinding.FragmentCartBinding
import com.example.shopapp.model.DatabaseHandler
import com.example.shopapp.model.adapters.CartAdapter
import com.example.shopapp.model.data.*
import com.example.shopapp.model.helper.OrderHelper
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.CartView
import com.example.shopapp.view.activity.HomeActivity
import com.example.shopapp.view.dialog.CheckoutDialog

class CartFragment(private val sharedPreferences: SharedPreferences) : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: CartAdapter
    lateinit var databaseHandler: DatabaseHandler
    private var cart = ArrayList<CartItem>()
    lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        databaseHandler = DatabaseHandler(ContextUtil.getHomeContext())
        communicator = activity as Communicator

        binding.rvShoppingCart.layoutManager = LinearLayoutManager(activity)

        getShoppingCart()

        binding.btnBack.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
        }

        binding.btnEmpty.setOnClickListener {
            clearShoppingCart()
        }

        binding.btnCheckout.setOnClickListener {
            if (cart.isNotEmpty()) {
                checkout()
            } else {
                Toast.makeText(context, "Please add at least one item to the cart", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun checkout() {
        val checkoutDialog = CheckoutDialog(ContextUtil.getHomeContext())

        checkoutDialog.setOnSuccessListener {
            val payment = Payment("cash")
            val itemList = ArrayList<Item>()
            var totalAmount = 0

            for (cartItem in cart) {
                val item = Item("", cartItem.itemPrice.toInt(), cartItem.itemName, cartItem.itemAmount)
                totalAmount += (cartItem.itemAmount * cartItem.itemPrice).toInt()
                itemList.add(item)
            }

            val orderSummary = OrderSummary(0, 0, totalAmount, totalAmount)
            val userId = sharedPreferences.getString("userid", "")
            val orderInfo = OrderInfo(orderSummary, payment, itemList, it, userId!!)

            communicator.toCheckoutPage(orderInfo)

            checkoutDialog.dismiss()
        }

        checkoutDialog.setOnCancelListener {
            checkoutDialog.dismiss()
        }

        checkoutDialog.show()
    }

    private fun clearShoppingCart() {
        val dialog = AlertDialog.Builder(ContextUtil.getHomeContext())
            .setTitle("Clear cart?")
            .setPositiveButton("Clear") { dialog, which ->
                databaseHandler.emptyCart()
                cart.clear()
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    private fun getShoppingCart() {
        Log.d("CartFragment", "getShoppingCart: ")
        cart = ArrayList(databaseHandler.getItems())
        Log.d("CartFragment", "getShoppingCart: ${cart.toString()}")
        adapter = CartAdapter(cart)
        adapter.setOnItemSelectedListener { cartItem, position ->
            val dialog = AlertDialog.Builder(ContextUtil.getHomeContext())
                .setTitle("Remove Item?")
                .setPositiveButton("Remove") { dialog, which ->
                    databaseHandler.deleteItem(cartItem)
                    cart.remove(cartItem)
                    adapter.notifyItemChanged(position)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }
        binding.rvShoppingCart.adapter = adapter
    }
}