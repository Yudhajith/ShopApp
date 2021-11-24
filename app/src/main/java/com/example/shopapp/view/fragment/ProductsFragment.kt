package com.example.shopapp.view.fragment

import android.content.Intent
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val subcategoryID = arguments?.getInt("subcategoryID")
        val subcategoryName = arguments?.getString("subcategoryName")
        ContextUtil.setProductContext(activity)
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        layoutBinding = ViewHolderProductsBinding.inflate(layoutInflater, container, false)

        binding.tvProducts.text = subcategoryName
        binding.rvProducts.layoutManager = LinearLayoutManager(activity)

        databaseHandler = createDataBaseHandler()

        presenter = ProductPresenter(this)
        presenter.getProducts(subcategoryID!!)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
        }
        return binding.root
    }



    override fun onSuccess(products: List<Product>) {
        adapter = ProductsAdapter(products)
        adapter.setOnProductSelectedListener { product, position ->
            Toast.makeText(context, "Selected item: ${product.productName}", Toast.LENGTH_SHORT).show()
            val addToCartDialog = AddToCartDialog(ContextUtil.getProductContext())

            addToCartDialog.setOnSuccessListener {
                val cartItem = CartItem(product.subId,
                    product.image,
                    product.productName,
                    product.price,
                    it)
                val success = databaseHandler.addItem(cartItem)
                if (success > 0) {
                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
                }
                addToCartDialog.dismiss()
            }
            addToCartDialog.show()
        }
        binding.rvProducts.adapter = adapter
    }

    private fun createDataBaseHandler(): DatabaseHandler {
        return DatabaseHandler(ContextUtil.getHomeContext())
    }
}