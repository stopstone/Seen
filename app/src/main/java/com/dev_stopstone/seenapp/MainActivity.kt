package com.dev_stopstone.seenapp

import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dev_stopstone.seenapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_home) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationHome.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_lost_detail ->
                    binding.bottomNavigationHome.visibility = GONE
                else ->
                    binding.bottomNavigationHome.visibility = VISIBLE
            }
        }
    }
}