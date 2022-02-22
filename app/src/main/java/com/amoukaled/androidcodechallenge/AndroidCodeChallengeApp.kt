package com.amoukaled.androidcodechallenge

import android.app.Application
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.repositories.AuthRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AndroidCodeChallengeApp : Application() {

    @Inject
    lateinit var dispatchers: DispatcherProvider

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate() {
        super.onCreate()

        val scope = CoroutineScope(dispatchers.io)
        scope.launch {
            authRepository.startup()
        }
    }

}