package com.amoukaled.androidcodechallenge.data.dataStore

interface TimerDataStore {

    /**
     * The accumulated time spent in app.
     */
    var accumulatedTime: Long

    /**
     * The newest session start timestamp.
     */
    var sessionStartTimestamp: Long

    /**
     * Clears all data related to the [TimerDataStore].
     */
    fun clear()

    /**
     * Starts session.
     */
    fun startSession()

    /**
     * Closes a session and increments the [accumulatedTime] accordingly.
     */
    fun closeSession()

}