package com.amoukaled.androidcodechallenge.repositories

import com.amoukaled.androidcodechallenge.models.AuthEvent
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    /**
     * Stateflow for the app authentication state.
     */
    val authStateFlow: StateFlow<AuthEvent>

    /**
     * Logs in the user with username and password.
     */
    suspend fun loginUser(inputUsername: String, inputPassword: String, inputRememberMe: Boolean)

    /**
     * @see AuthRepositoryImpl.checkRememberMe
     */
    suspend fun checkRememberMe()

    /**
     * Logs out the user and deletes all user-related info.
     */
    suspend fun logoutUser()

    /**
     * @see AuthRepositoryImpl.startup
     */
    suspend fun startup()

    /**
     * @see AuthRepositoryImpl.resume
     */
    fun resume()

    /**
     * @see AuthRepositoryImpl.pause
     */
    fun pause()

    /**
     * Debugs the user table and adds users if the table is empty.
     * @important This is for demo purposes only.
     */
    suspend fun debug()

    /**
     * @see AuthRepositoryImpl.username
     */
    val username: String?

    /**
     * @see AuthRepositoryImpl.firstLogin
     */
    val firstLogin: Long?

}