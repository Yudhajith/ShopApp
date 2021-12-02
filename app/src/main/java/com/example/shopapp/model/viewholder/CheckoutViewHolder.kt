package com.example.shopapp.model.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderCheckoutBinding
import com.example.shopapp.model.data.Item

class CheckoutViewHolder(val binding: ViewHolderCheckoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(item: Item) {
        Log.d("CartViewHolder", "bindData: ${item.toString()}")
        val price = item.price * item.quantity
        binding.tvItemName.text = item.productName
        binding.tvItemPrice.text = "\$${price}"
        binding.tvItemAmount.text = "Amount: ${item.quantity}"
    }
}