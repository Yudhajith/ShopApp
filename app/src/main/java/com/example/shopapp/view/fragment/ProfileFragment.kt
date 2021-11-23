package com.example.shopapp.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopapp.databinding.FragmentProfileBinding
import com.example.shopapp.view.activity.HomeActivity

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val sharedPreferences = activity?.getSharedPreferences("ShopApp", Context.MODE_PRIVATE)

        val fName = sharedPreferences?.getString("name", "")
        val email = sharedPreferences?.getString("email", "")
        val mobile = sharedPreferences?.getString("mobile", "")



        binding.apply {
            tvFName.text = fName
            tvEmail.text = email
            tvMobile.text = mobile

            btnExit.setOnClickListener {
                startActivity(Intent(activity, HomeActivity::class.java))
            }
        }



        return binding.root
    }

}