package com.example.shopapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityHomeBinding
import com.example.shopapp.model.CategoriesAdapter
import com.example.shopapp.model.Data
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.presenter.HomePresenter
import com.example.shopapp.utils.ContextUtil

class HomeActivity : AppCompatActivity(), HomeView, Communicator {

    lateinit var binding: ActivityHomeBinding
    lateinit var profileFragment: ProfileFragment
    lateinit var presenter: HomePresenter
    lateinit var adapter: CategoriesAdapter
    lateinit var subCategoryFragment: SubCategoryFragment
    lateinit var productsFragment: ProductsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileFragment = ProfileFragment()
        subCategoryFragment = SubCategoryFragment()
        productsFragment = ProductsFragment()

        setSupportActionBar(binding.toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        ContextUtil.setHomeContext(this)

        binding.rvCategories.layoutManager = GridLayoutManager(baseContext, 2)
        loadCategories()
        showSideBar()

    }

    private fun loadCategories() {
        presenter = HomePresenter(this)

        presenter.loadCategories()
    }

    private fun showSideBar() {
        binding.navView.setNavigationItemSelectedListener {
            it.isCheckable = true
            binding.drawerLayout.closeDrawers()

            when (it.itemId) {
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(binding.drawerLayout.id, profileFragment).commit()
                }
                R.id.shop -> {
                    binding.drawerLayout.closeDrawers()
                }
                R.id.cart -> {

                }
                R.id.logout -> {
                    val sharedPreferences = this.getSharedPreferences("ShopApp", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        binding.drawerLayout.openDrawer(binding.navView)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSuccess(categories: List<Data>) {
        adapter = CategoriesAdapter(categories)
        adapter.setOnCategorySelectedListener { category, position ->
            val bundle = Bundle()
            val imageUrl = "https://rjtmobile.com/grocery/images/${category.catImage}"
            bundle.putInt("categoryID", category.catId)
            bundle.putString("categoryName", category.catName)
            bundle.putString("categoryImage", imageUrl)
            subCategoryFragment.arguments = bundle
            binding.root.removeAllViews()
            supportFragmentManager.beginTransaction().replace(binding.drawerLayout.id, subCategoryFragment).commit()
        }
        binding.rvCategories.adapter = adapter
    }

    override fun toProductsPage(subcategoryID: Int, categoryID: Int, subcategoryName: String) {
        val bundle = Bundle()
        bundle.putInt("subcategoryID", subcategoryID)
        bundle.putInt("categoryID", categoryID)
        bundle.putString("subcategoryName", subcategoryName)
        val transaction = this.supportFragmentManager.beginTransaction()
        productsFragment.arguments = bundle
        transaction.replace(binding.drawerLayout.id, productsFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }


}
