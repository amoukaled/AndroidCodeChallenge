package com.amoukaled.androidcodechallenge.viewModels

import androidx.lifecycle.ViewModel
import com.amoukaled.androidcodechallenge.repositories.AuthRepository
import com.amoukaled.androidcodechallenge.repositories.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(authRepository: AuthRepository, private val timerRepository: TimerRepository) : ViewModel() {

    /**
     * @see [AuthRepository.firstLogin]
     */
    val firstLogin: Long? = authRepository.firstLogin

    /**
     * @see TimerRepository.getCurrentTimeSpent
     */
    fun currentTimeSpent() = timerRepository.getCurrentTimeSpent()

}