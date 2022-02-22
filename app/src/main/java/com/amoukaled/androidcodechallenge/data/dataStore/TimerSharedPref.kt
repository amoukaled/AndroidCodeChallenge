package com.amoukaled.androidcodechallenge.data.dataStore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.amoukaled.androidcodechallenge.utils.CustomDateUtils

/**
 * [TimerDataStore] implementation with [SharedPreferences].
 */
class TimerSharedPref(private val context: Context) : TimerDataStore {

    companion object {
        // static keys
        const val TIMER_SHARED_PREF_NAME = "sdgfds"
        const val ACCUMULATED_TIME_KEY = "jukjhjh"
        const val SESSION_START_KEY = "tghgjh"
    }

    /**
     * Return the [SharedPreferences].
     */
    private fun getSharedPref(): SharedPreferences = context.getSharedPreferences(TIMER_SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override var accumulatedTime: Long
        get() = getSharedPref().getLong(ACCUMULATED_TIME_KEY, 0)
        set(value) {
            getSharedPref().edit {
                putLong(ACCUMULATED_TIME_KEY, value)
                apply()
            }
        }

    override var sessionStartTimestamp: Long
        get() = getSharedPref().getLong(SESSION_START_KEY, 0)
        set(value) {
            getSharedPref().edit {
                putLong(SESSION_START_KEY, value)
                apply()
            }
        }

    override fun clear() {
        // clear both accumulated time and start session
        accumulatedTime = 0
        sessionStartTimestamp = 0
    }

    override fun startSession() {
        sessionStartTimestamp = CustomDateUtils.now()
    }

    override fun closeSession() {
        // if session is set get the difference and increment
        if (sessionStartTimestamp > 0) {
            val now = CustomDateUtils.now()
            val duration = now - sessionStartTimestamp
            if (duration > 0) {
                accumulatedTime += duration
            }

            // resetting
            sessionStartTimestamp = 0
        }
    }
}