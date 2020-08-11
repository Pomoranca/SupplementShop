package com.coffeetime.supplementshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.coffeetime.supplementshop.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager
            .findFragmentById(R.id.homeFragment) as NavHostFragment
        val navController = host.navController
        NavigationUI.setupWithNavController(bottomNavigation, navController)

    }
}