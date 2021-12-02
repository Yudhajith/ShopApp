package com.example.shopapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.databinding.ViewHolderProductsBinding
import com.example.shopapp.model.data.CartItem
import com.example.shopapp.model.data.Product
import com.example.shopapp.model.viewholder.ProductsViewHolder

class ProductsAdapter(val products: List<Product>) : RecyclerView.Adapter<ProductsViewHolder>() {

    private lateinit var onProductSelected: (Product, Int, Int) -> Unit

    fun setOnProductSelectedListener(listener: (Product, Int, Int) -> Unit) {
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

        holder.binding.btnIncrement.setOnClickListener {
            val amount = holder.binding.tvAmount.text.toString().toInt()

            holder.binding.tvAmount.text = (amount + 1).toString()
        }

        holder.binding.btnDecrement.setOnClickListener {
            val amount = holder.binding.tvAmount.text.toString().toInt()

            holder.binding.tvAmount.text = (amount - 1).toString()
        }

        holder.binding.btnAddToCart.setOnClickListener {
            val amount = holder.binding.tvAmount.text.toString().toInt()

            if (amount > 0) {
                holder.binding.tvAmount.text = "0"
                onProductSelected(product, position, amount)
            } else {
                holder.binding.tvAmount.text = "0"
                onProductSelected(product, position, 0)
            }
        }

    }

    override fun getItemCount() = products.size
}