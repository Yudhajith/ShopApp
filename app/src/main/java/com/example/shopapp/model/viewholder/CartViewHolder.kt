package com.example.shopapp.model.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderShoppingCartBinding
import com.example.shopapp.model.data.CartItem
import com.squareup.picasso.Picasso

class CartViewHolder(val binding: ViewHolderShoppingCartBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(item: CartItem) {
        Log.d("CartViewHolder", "bindData: ${item.toString()}")
        val imageUrl = "https://rjtmobile.com/grocery/images/${item.itemImage}"
        Picasso
            .get()
            .load(imageUrl)
            .into(binding.ivItem)
        val price = item.itemPrice * item.itemAmount
        binding.tvItemName.text = item.itemName
        binding.tvItemPrice.text = "\$${price}"
        binding.tvItemAmount.text = "x${item.itemAmount}"
    }
}