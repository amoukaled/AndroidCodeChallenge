package com.amoukaled.androidcodechallenge.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.amoukaled.androidcodechallenge.R
import com.amoukaled.androidcodechallenge.databinding.ActivityLoginBinding
import com.amoukaled.androidcodechallenge.models.AuthEvent
import com.amoukaled.androidcodechallenge.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // init
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collectAuthFlow()
        initRememberMe()
        initLoginButton()
    }

    /**
     * @see [LoginViewModel.pause]
     */
    private fun pauseAuthListener() {
        model.pause()
    }

    /**
     * @see [LoginViewModel.resume]
     */
    private fun resumeAuthListener() {
        model.resume()
    }

    /**
     * Initializes [ActivityLoginBinding.loginButton].
     * @note Validates input before logging in.
     */
    private fun initLoginButton() {
        binding.apply {
            loginButton.setOnClickListener {
                usernameET.text?.toString()?.let { username ->
                    if (username.isBlank()) {
                        usernameET.error = getString(R.string.field_mandatory)
                    } else {
                        usernameET.error = null
                        passwordET.text?.toString()?.let { password ->
                            if (password.isBlank()) {
                                passwordET.error = getString(R.string.field_mandatory)
                            } else {
                                passwordET.error = null
                                model.login(username, password)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Initializes [ActivityLoginBinding.rememberSW].
     */
    private fun initRememberMe() {
        binding.rememberSW.isChecked = model.rememberMe
        binding.rememberSW.setOnCheckedChangeListener { _, isChecked ->
            model.rememberMe = isChecked
        }
    }

    /**
     * Collects [LoginViewModel.authStateFlow].
     */
    private fun collectAuthFlow() {
        lifecycleScope.launchWhenCreated {
            model.authStateFlow.collect { event ->
                when (event) {
                    is AuthEvent.Authenticated -> {
                        Intent(this@LoginActivity, MainActivity::class.java).also {
                            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(it)
                            finish()
                        }
                    }
                    is AuthEvent.Error -> {
                        binding.apply {
                            progressbar.isGone = true
                            errorTV.isVisible = true
                            usernameHolder.isVisible = true
                            passwordHolder.isVisible = true
                            rememberSW.isVisible = true
                            loginButton.isVisible = true
                            errorTV.text = event.message ?: "Something went wrong"
                        }
                    }
                    is AuthEvent.UnAuthenticated -> {
                        binding.apply {
                            progressbar.isGone = true
                            errorTV.isGone = true
                            usernameHolder.isVisible = true
                            passwordHolder.isVisible = true
                            rememberSW.isVisible = true
                            loginButton.isVisible = true
                        }
                    }
                    is AuthEvent.Loading -> {
                        binding.apply {
                            progressbar.isVisible = true
                            errorTV.isGone = true
                            usernameHolder.isGone = true
                            passwordHolder.isGone = true
                            rememberSW.isGone = true
                            loginButton.isGone = true
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resumeAuthListener()
    }

    override fun onDestroy() {
        pauseAuthListener()
        super.onDestroy()
    }

    override fun onPause() {
        pauseAuthListener()
        super.onPause()
    }

}