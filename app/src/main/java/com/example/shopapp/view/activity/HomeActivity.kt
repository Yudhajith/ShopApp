package com.example.shopapp.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityHomeBinding
import com.example.shopapp.model.adapters.CategoriesAdapter
import com.example.shopapp.model.data.Data
import com.example.shopapp.model.data.OrderInfo
import com.example.shopapp.presenter.Communicator
import com.example.shopapp.presenter.HomePresenter
import com.example.shopapp.utils.ContextUtil
import com.example.shopapp.view.*
import com.example.shopapp.view.dialog.ProfileDialog
import com.example.shopapp.view.fragment.*

class HomeActivity : AppCompatActivity(), HomeView, Communicator {

    lateinit var binding: ActivityHomeBinding
    lateinit var profileFragment: ProfileFragment
    lateinit var presenter: HomePresenter
    lateinit var adapter: CategoriesAdapter
    lateinit var subCategoryFragment: SubCategoryFragment
    lateinit var productsFragment: ProductsFragment
    lateinit var cartFragment: CartFragment
    lateinit var checkoutFragment: CheckoutFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileFragment = ProfileFragment()
        subCategoryFragment = SubCategoryFragment()
        productsFragment = ProductsFragment()

        val sharedPreferences = getSharedPreferences("ShopApp", Context.MODE_PRIVATE)
        cartFragment = CartFragment(sharedPreferences)


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
                    val sharedPreferences = getSharedPreferences("ShopApp", Context.MODE_PRIVATE)
                    val profileDialog = ProfileDialog(this, sharedPreferences)

                    profileDialog.setOnSuccessListener {
                        profileDialog.dismiss()
                    }

                    profileDialog.show()
                }
                R.id.shop -> {
                    binding.drawerLayout.closeDrawers()
                }
                R.id.cart -> {
                    binding.root.removeAllViews()
                    supportFragmentManager.beginTransaction().replace(binding.drawerLayout.id, cartFragment).commit()
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
            finishAffinity()
            finish()
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

    override fun toProductsPage(subcategoryID: Int, categoryID: Int, subcategoryName: String, catImage: String) {
        val bundle = Bundle()
        bundle.putInt("subcategoryID", subcategoryID)
        bundle.putInt("categoryID", categoryID)
        bundle.putString("categoryImage", catImage)
        bundle.putString("subcategoryName", subcategoryName)
        val transaction = this.supportFragmentManager.beginTransaction()
        productsFragment.arguments = bundle
        transaction.replace(binding.drawerLayout.id, productsFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    override fun toCheckoutPage(orderInfo: OrderInfo) {
        checkoutFragment = CheckoutFragment(orderInfo)
        binding.root.removeAllViews()
        supportFragmentManager.beginTransaction().replace(binding.drawerLayout.id, checkoutFragment).commit()
    }

    override fun toCartPage() {
        supportFragmentManager.beginTransaction().replace(binding.drawerLayout.id, cartFragment).commit()
    }

    override fun toSubCatPage(
        subcategoryID: Int,
        subcategoryName: String,
        subcategoryImage: String
    ) {
        val bundle = Bundle()
        bundle.putInt("categoryID", subcategoryID)
        bundle.putString("categoryName", subcategoryName)
        bundle.putString("categoryImage", subcategoryImage)
        subCategoryFragment.arguments = bundle
        binding.root.removeAllViews()
        supportFragmentManager.beginTransaction().replace(binding.drawerLayout.id, subCategoryFragment).commit()
    }

}
