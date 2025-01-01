package com.example.myapplication

import SharedViewModel
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.calendar.CalendarViewModel
import com.example.myapplication.ui.gallery.DialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedDateViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Clear restored fragments
        if (savedInstanceState != null) {
            val fragmentManager = supportFragmentManager
            val restoredFragment = fragmentManager.findFragmentByTag("CustomDialog")
            if (restoredFragment is DialogFragment) {
                fragmentManager.beginTransaction().remove(restoredFragment).commitNowAllowingStateLoss()
            }
        }

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_contact, R.id.navigation_calendar, R.id.navigation_gallery
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onPause() {
        super.onPause()
        // Dismiss all dialog fragments
        val fragmentManager = supportFragmentManager
        fragmentManager.fragments.forEach { fragment ->
            if (fragment is DialogFragment) {
                fragment.dismissAllowingStateLoss()
            }
        }
    }
}