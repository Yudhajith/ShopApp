package com.example.shopapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.databinding.FragmentProductsBinding
import com.example.shopapp.model.Product
import com.example.shopapp.model.ProductsAdapter
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.presenter.ProductPresenter
import com.example.shopapp.utils.ContextUtil

class ProductsFragment : Fragment(), ProductView {

    lateinit var binding: FragmentProductsBinding
    lateinit var adapter: ProductsAdapter
    lateinit var presenter: ProductPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val subcategoryID = arguments?.getInt("subcategoryID")
        val subcategoryName = arguments?.getString("subcategoryName")
        ContextUtil.setProductContext(activity)
        binding = FragmentProductsBinding.inflate(inflater, container, false)

        binding.tvProducts.text = subcategoryName
        binding.rvProducts.layoutManager = LinearLayoutManager(activity)

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
            Toast.makeText(activity, "Selected product: ${product.productName}", Toast.LENGTH_SHORT).show()
        }
        binding.rvProducts.adapter = adapter
    }


}