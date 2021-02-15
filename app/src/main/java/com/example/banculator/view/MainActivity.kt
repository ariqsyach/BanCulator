package com.example.banculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.banculator.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        navigateFragment(HomeFragment())
//        supportActionBar?.title = "Home"

        bottom_nav.itemIconTintList = null
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.home_menu -> {
                    navigateFragment(CalculatorFragment())
                    supportActionBar?.title = "Home"
                }
                R.id.result_menu -> {
                    navigateFragment(FeedsFragment())
                    supportActionBar?.title = "Result"
                }
                R.id.database_menu -> {
                    navigateFragment(ProfileFragment())
                    supportActionBar?.title = "Database"
                }
            }
            true
        }
    }

    private fun navigateFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
    }
}

