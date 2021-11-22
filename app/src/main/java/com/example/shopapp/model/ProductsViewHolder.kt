package com.example.shopapp.model

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderProductsBinding
import com.squareup.picasso.Picasso

class ProductsViewHolder(val binding: ViewHolderProductsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(product: Product) {
        binding.tvProductName.text = product.productName
        binding.tvProductDescription.text = product.description
        val imageUrl = "https://rjtmobile.com/grocery/images/${product.image}"
        Picasso
            .get()
            .load(imageUrl)
            .into(binding.ivProduct)
    }
}