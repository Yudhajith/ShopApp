package com.example.shopapp.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderCategoriesBinding

class CategoriesAdapter(val categories: List<Data>) : RecyclerView.Adapter<CategoriesViewHolder>() {

    private lateinit var onCategorySelected: (Data, Int) -> Unit

    fun setOnCategorySelectedListener(listener: (Data, Int) -> Unit) {
        this.onCategorySelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCategoriesBinding.inflate(layoutInflater, parent, false)

        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = categories[position]
        holder.bindData(category)

        holder.itemView.setOnClickListener {
            if (this::onCategorySelected.isInitialized) {
                onCategorySelected(category, position)
            }
        }
    }

    override fun getItemCount() = categories.size
}