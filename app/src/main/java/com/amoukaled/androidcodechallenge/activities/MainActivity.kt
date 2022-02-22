package com.amoukaled.androidcodechallenge.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.amoukaled.androidcodechallenge.R

import com.amoukaled.androidcodechallenge.databinding.ActivityMainBinding
import com.amoukaled.androidcodechallenge.databinding.NavHeaderMainBinding
import com.amoukaled.androidcodechallenge.models.AuthEvent
import com.amoukaled.androidcodechallenge.utils.DialogUtils
import com.amoukaled.androidcodechallenge.viewModels.MainViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        updateHeaderUsername()
        startSession()

        // listen to auth changes
        resumeAuthListener()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_main)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_timer, R.id.nav_list_view), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // logout callback
        navView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.nav_logout) {
                DialogUtils.showAlertDialog(
                    this@MainActivity,
                    getString(R.string.logout),
                    getString(R.string.logout_prompt),
                    getString(R.string.cancel),
                    { dialog, _ ->
                        dialog.dismiss()
                    },
                    getString(R.string.logout),
                    { dialog, _ ->
                        model.logout()
                        dialog.dismiss()
                    },
                    R.drawable.dialog_background,
                    R.drawable.ic_logout
                )
            }

            // maintaining the behavior of the Navigation view
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            // closing the drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }


        collectAuthEvents()
    }

    /**
     * @see [MainViewModel.startSession]
     */
    private fun startSession() {
        model.startSession()
    }

    /**
     * Gets the nav header and sets the username.
     */
    private fun updateHeaderUsername() {
        val headerView = binding.navView.getHeaderView(0)
        val navViewHeaderBinding: NavHeaderMainBinding = NavHeaderMainBinding.bind(headerView)
        navViewHeaderBinding.usernameTV.text = model.username
    }

    /**
     * Collects [MainViewModel.authStateFlow]
     */
    private fun collectAuthEvents() {
        lifecycleScope.launchWhenCreated {
            model.authStateFlow.collect { event ->
                when (event) {
                    is AuthEvent.Authenticated -> Unit
                    else -> {
                        Intent(this@MainActivity, LoginActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    }
                }
            }
        }
    }

    /**
     * @see [MainViewModel.closeSession]
     */
    private fun closeSession() {
        model.closeSession()
    }

    /**
     * @see [MainViewModel.pause]
     */
    private fun pauseAuthListener() {
        model.pause()
    }

    /**
     * @see [MainViewModel.resume]
     */
    private fun resumeAuthListener() {
        model.resume()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Asks the user if they want to quit the app if the timer fragment is in view, else navigates to the timer
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_main)
        when (navController.currentDestination?.id) {
            R.id.nav_timer -> {
                DialogUtils.showAlertDialog(
                    this@MainActivity,
                    getString(R.string.quit),
                    getString(R.string.close_prompt),
                    getString(R.string.cancel),
                    { dialog, _ ->
                        dialog.dismiss()
                    },
                    getString(R.string.quit),
                    { dialog, _ ->
                        dialog.dismiss()
                        // close all activities
                        finishAffinity()

                        // exit app after 200 ms, this way there is enough
                        // time to close the session
                        // and increment the timer
                        Handler(Looper.getMainLooper()).postDelayed({
                            exitProcess(0)
                        }, 200)
                    },
                    R.drawable.dialog_background,
                    R.drawable.ic_logout
                )
            }
            else -> {
                navController.navigate(R.id.nav_timer)
            }
        }
    }

    override fun onPause() {
        pauseAuthListener()
        closeSession()
        super.onPause()
    }

    override fun finish() {
        closeSession()
        super.finish()
    }

    override fun onResume() {
        super.onResume()
        resumeAuthListener()
        startSession()
    }

}