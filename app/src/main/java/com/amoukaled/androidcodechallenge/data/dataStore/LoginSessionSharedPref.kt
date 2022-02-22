package com.amoukaled.androidcodechallenge.data.dataStore

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * [LoginSessionDataStore] implementation with [SharedPreferences].
 */
class LoginSessionSharedPref(private val context: Context) : LoginSessionDataStore {

    @Suppress("SpellCheckingInspection")
    companion object {
        // static keys
        private const val LOGIN_SHARED_PREF_NAME = "ygdslch"
        private const val USERNAME_KEY = "SDSF3"
        private const val LOGIN_TIME_KEY = "SD3HRFD"
        private const val LOGOUT_TIME_KEY = "JHFJGFJ"
        private const val REMEMBER_ME = "ygdsGHFHGlch"
        private const val FIRST_LOGIN = "43RFDFD"
    }

    /**
     * Gets the [SharedPreferences] from [context].
     */
    private fun getSharedPref(): SharedPreferences = context.getSharedPreferences(LOGIN_SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override var username: String?
        get() = getSharedPref().getString(USERNAME_KEY, null)
        set(value) {
            getSharedPref().edit {
                putString(USERNAME_KEY, value)
                apply()
            }
        }

    override var loginTime: Long?
        get() {
            val savedValue = getSharedPref().getLong(LOGIN_TIME_KEY, -1)
            return if (savedValue == -1L) null else savedValue
        }
        set(value) {
            getSharedPref().edit {
                putLong(LOGIN_TIME_KEY, value ?: -1)
                apply()
            }
        }

    override var logoutTime: Long?
        get() {
            val savedValue = getSharedPref().getLong(LOGOUT_TIME_KEY, -1)
            return if (savedValue == -1L) null else savedValue
        }
        set(value) {
            getSharedPref().edit {
                putLong(LOGOUT_TIME_KEY, value ?: -1)
                apply()
            }
        }

    override var rememberMe: Boolean
        get() = getSharedPref().getBoolean(REMEMBER_ME, true)
        set(value) {
            getSharedPref().edit {
                putBoolean(REMEMBER_ME, value)
                apply()
            }
        }

    override var firstAppLogin: Long?
        get() {
            val savedValue = getSharedPref().getLong(FIRST_LOGIN, -1)
            return if (savedValue == -1L) null else savedValue
        }
        set(value) {
            getSharedPref().edit {
                putLong(FIRST_LOGIN, value ?: -1)
                apply()
            }
        }

    override fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        getSharedPref().registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        getSharedPref().unregisterOnSharedPreferenceChangeListener(listener)
    }
}