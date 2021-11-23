package com.example.shopapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderSubcategoryBinding
import com.example.shopapp.model.data.SubCategory
import com.example.shopapp.model.viewholder.SubcategoriesViewHolder

class SubcategoriesAdapter(val subcategories: List<SubCategory>, val categoryImage: String) : RecyclerView.Adapter<SubcategoriesViewHolder>() {

    private lateinit var onsubcategorySelected: (SubCategory, Int) -> Unit

    fun setOnSubcategorySelectedListener(listener: (SubCategory, Int) -> Unit) {
        this.onsubcategorySelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderSubcategoryBinding.inflate(layoutInflater, parent, false)

        return SubcategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubcategoriesViewHolder, position: Int) {
        val subcategory = subcategories[position]
        holder.bindData(subcategory, categoryImage)

        holder.itemView.setOnClickListener {
            if (this::onsubcategorySelected.isInitialized) {
                onsubcategorySelected(subcategory, position)
            }
        }
    }

    override fun getItemCount() = subcategories.size
}