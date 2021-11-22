package com.example.shopapp.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderProductsBinding

class ProductsAdapter(val products: List<Product>) : RecyclerView.Adapter<ProductsViewHolder>() {

    private lateinit var onProductSelected: (Product, Int) -> Unit

    fun setOnProductSelectedListener(listener: (Product, Int) -> Unit) {
        this.onProductSelected = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderProductsBinding.inflate(layoutInflater, parent, false)

        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.bindData(product)

        holder.itemView.setOnClickListener {
            if (this::onProductSelected.isInitialized) {
                onProductSelected(product, position)
            }
        }
    }

    override fun getItemCount() = products.size
}