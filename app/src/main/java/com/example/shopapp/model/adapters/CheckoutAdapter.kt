package com.example.shopapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderCheckoutBinding
import com.example.shopapp.model.data.Item
import com.example.shopapp.model.viewholder.CheckoutViewHolder

class CheckoutAdapter(val items: List<Item>) : RecyclerView.Adapter<CheckoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCheckoutBinding.inflate(layoutInflater, parent, false)

        return CheckoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val item = items[position]
        holder.bindData(item)

    }

    override fun getItemCount() = items.size
}