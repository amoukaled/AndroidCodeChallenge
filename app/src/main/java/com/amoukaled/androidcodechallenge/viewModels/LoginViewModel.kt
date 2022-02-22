package com.amoukaled.androidcodechallenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository, private val dispatchers: DispatcherProvider) : ViewModel() {

    /**
     * @see [AuthRepository.authStateFlow]
     */
    val authStateFlow = authRepository.authStateFlow

    var rememberMe = false

    /**
     * @see [AuthRepository.loginUser]
     */
    fun login(username: String, password: String) {
        viewModelScope.launch(dispatchers.io) {
            authRepository.loginUser(username, password, rememberMe)
        }
    }

    /**
     * @see [AuthRepository.resume]
     */
    fun resume() = authRepository.resume()

    /**
     * @see [AuthRepository.pause]
     */
    fun pause() = authRepository.pause()

}