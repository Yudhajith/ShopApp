package com.example.shopapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.databinding.FragmentCartBinding
import com.example.shopapp.model.DatabaseHandler
import com.example.shopapp.model.adapters.CartAdapter
import com.example.shopapp.model.data.CartItem
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.activity.HomeActivity

class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    lateinit var adapter: CartAdapter
    lateinit var databaseHandler: DatabaseHandler
    var cart = ArrayList<CartItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        databaseHandler = DatabaseHandler(ContextUtil.getHomeContext())

        binding.rvShoppingCart.layoutManager = LinearLayoutManager(activity)

        getShoppingCart()

        binding.btnBack.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
        }

        binding.btnEmpty.setOnClickListener {
            clearShoppingCart()
        }

        return binding.root
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