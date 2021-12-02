package com.example.shopapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.databinding.DialogAddToCartBinding
import com.example.shopapp.databinding.FragmentProductsBinding
import com.example.shopapp.databinding.ViewHolderProductsBinding
import com.example.shopapp.model.data.CartItem
import com.example.shopapp.model.DatabaseHandler
import com.example.shopapp.model.data.Product
import com.example.shopapp.model.adapters.ProductsAdapter
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.presenter.ProductPresenter
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.dialog.AddToCartDialog
import com.example.shopapp.view.ProductView
import com.example.shopapp.view.activity.HomeActivity

class ProductsFragment : Fragment(), ProductView {

    lateinit var binding: FragmentProductsBinding
    lateinit var layoutBinding: ViewHolderProductsBinding
    lateinit var adapter: ProductsAdapter
    lateinit var presenter: ProductPresenter
    lateinit var databaseHandler: DatabaseHandler
    lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val subcategoryID = arguments?.getInt("subcategoryID")
        val subcategoryName = arguments?.getString("subcategoryName")
        val subcategoryImage = arguments?.getString("categoryImage")
        ContextUtil.setProductContext(activity)
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        layoutBinding = ViewHolderProductsBinding.inflate(layoutInflater, container, false)

        communicator = activity as Communicator

        binding.tvProducts.text = subcategoryName
        binding.rvProducts.layoutManager = LinearLayoutManager(activity)

        databaseHandler = createDataBaseHandler()

        presenter = ProductPresenter(this)
        presenter.getProducts(subcategoryID!!)

        binding.btnBack.setOnClickListener {
            val categoryID = arguments?.getInt("categoryID")
            communicator.toSubCatPage(categoryID!!, subcategoryName!!, subcategoryImage!!)
        }
        return binding.root
    }



    override fun onSuccess(products: List<Product>) {
        adapter = ProductsAdapter(products)
        adapter.setOnProductSelectedListener { product, position, amount ->
            if (amount > 0) {
                val cartItem = CartItem(((product.subId * 10) + position),
                    product.image,
                    product.productName,
                    product.price,
                    amount)
                val success = databaseHandler.addItem(cartItem)
                if (success > 0) {
                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Minimum amount is 1", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvProducts.adapter = adapter
    }

    private fun createDataBaseHandler(): DatabaseHandler {
        return DatabaseHandler(ContextUtil.getHomeContext())
    }
}