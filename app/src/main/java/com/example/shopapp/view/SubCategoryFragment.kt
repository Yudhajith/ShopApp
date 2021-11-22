package com.example.shopapp.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopapp.databinding.FragmentSubcategoriesBinding
import com.example.shopapp.model.SubCategory
import com.example.shopapp.model.SubcategoriesAdapter
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.presenter.SubcategoryPresenter
import com.example.shopapp.utils.ContextUtil

class SubCategoryFragment : Fragment(), SubcategoryView {

    lateinit var binding: FragmentSubcategoriesBinding
    lateinit var adapter: SubcategoriesAdapter
    lateinit var presenter: SubcategoryPresenter
    lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val catName = arguments?.getString("categoryName")
        val catId = arguments?.getInt("categoryID")
        ContextUtil.setSubcategoryContext(activity)
        binding = FragmentSubcategoriesBinding.inflate(layoutInflater, container, false)
        communicator = activity as Communicator

        binding.tvSubcategory.text = catName
        binding.rvSubcategories.layoutManager = LinearLayoutManager(activity)

        presenter = SubcategoryPresenter(this)
        presenter.getSubcategories(catId!!)

        binding.btnHome.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
        }

        return binding.root
    }

    override fun onSuccess(subcategories: List<SubCategory>) {
        val catImage = arguments?.getString("categoryImage", "")
        adapter = SubcategoriesAdapter(subcategories, catImage!!)
        adapter.setOnSubcategorySelectedListener { subCategory, position ->
            communicator.toProductsPage(subCategory.subId, subCategory.catId, subCategory.subName)
        }
        binding.rvSubcategories.adapter = adapter
    }
}