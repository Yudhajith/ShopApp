package com.example.shopapp.model.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderSubcategoryBinding
import com.example.shopapp.model.data.SubCategory
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