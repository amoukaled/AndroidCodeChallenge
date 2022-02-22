package com.amoukaled.androidcodechallenge.repositories

interface TimerRepository {

    /**
     * Gets the time spent in app.
     */
    fun getCurrentTimeSpent(): Long

    /**
     * Closes the login session.
     */
    fun closeSession()

    /**
     * Starts the timer session.
     */
    fun startSession()

}