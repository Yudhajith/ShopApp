package com.example.shopapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderShoppingCartBinding
import com.example.shopapp.model.data.CartItem
import com.example.shopapp.model.viewholder.CartViewHolder

class CartAdapter(val cart: List<CartItem>) : RecyclerView.Adapter<CartViewHolder>() {

    private lateinit var onItemSelected: (CartItem, Int) -> Unit

    fun setOnItemSelectedListener(listener: (CartItem, Int) -> Unit) {
        this.onItemSelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderShoppingCartBinding.inflate(layoutInflater, parent, false)

        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cart[position]
        holder.bindData(item)

        holder.itemView.setOnClickListener {
            if (this::onItemSelected.isInitialized) {
                onItemSelected(item, position)
            }
        }
    }

    override fun getItemCount() = cart.size
}