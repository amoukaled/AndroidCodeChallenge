package com.amoukaled.androidcodechallenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.repositories.AuthRepository
import com.amoukaled.androidcodechallenge.repositories.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dispatchers: DispatcherProvider,
    private val timerRepository: TimerRepository
) : ViewModel() {

    /**
     * @see [AuthRepository.authStateFlow]
     */
    val authStateFlow = authRepository.authStateFlow

    /**
     * @see [AuthRepository.resume]
     */
    fun resume() = authRepository.resume()

    /**
     * @see [AuthRepository.pause]
     */
    fun pause() = authRepository.pause()

    /**
     * @see [AuthRepository.logoutUser]
     */
    fun logout() {
        viewModelScope.launch(dispatchers.io) {
            authRepository.logoutUser()
        }
    }

    /**
     * @see [TimerRepository.startSession]
     */
    fun startSession() {
        timerRepository.startSession()
    }

    /**
     * @see [TimerRepository.closeSession]
     */
    fun closeSession() {
        timerRepository.closeSession()
    }

    /**
     * @see [AuthRepository.username]
     */
    val username = authRepository.username

}