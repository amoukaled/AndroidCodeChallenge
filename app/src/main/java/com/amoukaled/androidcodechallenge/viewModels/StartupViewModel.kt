package com.amoukaled.androidcodechallenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val authRepository: AuthRepository
) : ViewModel() {

    /**
     * @see [AuthRepository.authStateFlow]
     */
    val authStateFlow = authRepository.authStateFlow

    /**
     * @see [AuthRepository.debug]
     */
    fun debugAuthRepository() {
        viewModelScope.launch(dispatchers.io) {
            authRepository.debug()
        }
    }
}