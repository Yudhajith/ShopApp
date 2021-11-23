package com.example.shopapp.model.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderCategoriesBinding
import com.example.shopapp.model.data.Data
import com.squareup.picasso.Picasso

class CategoriesViewHolder(val binding: ViewHolderCategoriesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(category: Data) {
        val imageUrl = "https://rjtmobile.com/grocery/images/${category.catImage}"
        Picasso
            .get()
            .load(imageUrl)
            .into(binding.ivCategory)
        binding.tvCategoryName.text = category.catName

    }
}