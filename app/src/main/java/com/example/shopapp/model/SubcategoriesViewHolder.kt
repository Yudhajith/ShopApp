package com.example.shopapp.model

import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderSubcategoryBinding
import com.squareup.picasso.Picasso

class SubcategoriesViewHolder(val binding: ViewHolderSubcategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(subCategory: SubCategory, categoryImage: String) {
        binding.tvSubcategoryName.text = subCategory.subName
        Picasso
            .get()
            .load(categoryImage)
            .into(binding.ivSubcategory)
    }
}