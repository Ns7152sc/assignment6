package com.example.weatherapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.weatherapplication.databinding.ActivityMainBinding
import com.example.weatherapplication.model.CurrentConditions
import com.example.weatherapplication.model.Forecast
import com.example.weatherapplication.network.*
import com.example.weatherapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var navController: NavController? = null
    private var navHostFragment: NavHostFragment? = null
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Search"

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_id) as NavHostFragment
        val inflater = navHostFragment?.navController?.navInflater
        val graph = inflater?.inflate(R.navigation.navigation_graph)

        //graph?.startDestination = R.id.searchFragment
        graph?.let {
            navHostFragment?.navController?.graph = it
        }

        navController = findNavController(R.id.nav_host_fragment_id)

        initListeners()
    }


    private fun initListeners() {

    }


}