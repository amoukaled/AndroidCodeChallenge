package com.amoukaled.androidcodechallenge.repositories

import com.amoukaled.androidcodechallenge.data.dataStore.TimerDataStore
import com.amoukaled.androidcodechallenge.utils.CustomDateUtils

class TimerRepositoryImpl(private val timerDataStore: TimerDataStore) : TimerRepository {

    override fun getCurrentTimeSpent(): Long {
        // get the accumulated value and current session time
        val accumulatedValue = timerDataStore.accumulatedTime
        val sessionStartTime = timerDataStore.sessionStartTimestamp

        // get the live timer
        return if (sessionStartTime > 0) {
            val now = CustomDateUtils.now()
            val currentRunningTime = now - sessionStartTime
            accumulatedValue + currentRunningTime
        } else {
            0L
        }
    }

    override fun startSession() {
        timerDataStore.startSession()
    }

    override fun closeSession() {
        timerDataStore.closeSession()
    }

}