package com.example.feedthenyusha

import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.feedthenyusha.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        showSplashIfNecessary(binding, savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()

    private fun showSplashIfNecessary(binding: ActivityMainBinding, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val transitionDrawable = window.decorView.background as TransitionDrawable
            transitionDrawable.startTransition(logoCrossFadeDurationMillis)

            lifecycleScope.launch {
                delay(logoCrossFadeDurationMillis.toLong() + spacingAfterFadeDurationMillis)
                updateBackground()
                setContentView(binding.root)
            }
        } else {
            updateBackground()
            setContentView(binding.root)
        }
    }

    private fun updateBackground() {
        window.decorView.background =
            AppCompatResources.getDrawable(this, R.drawable.splash_background)
    }

    private companion object {
        private const val logoCrossFadeDurationMillis = 1000
        private const val spacingAfterFadeDurationMillis = 500
    }
}