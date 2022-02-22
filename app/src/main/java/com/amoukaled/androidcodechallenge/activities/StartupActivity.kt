package com.amoukaled.androidcodechallenge.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amoukaled.androidcodechallenge.models.AuthEvent
import com.amoukaled.androidcodechallenge.viewModels.StartupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartupActivity : AppCompatActivity() {

    private val viewModel: StartupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.debugAuthRepository()
        checkAuthState()
    }

    private fun checkAuthState() {
        when (viewModel.authStateFlow.value) {
            is AuthEvent.Authenticated -> {
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            else -> {
                Intent(this, LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

}