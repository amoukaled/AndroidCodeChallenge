package com.amoukaled.androidcodechallenge.data.dataStore

import android.content.SharedPreferences

interface LoginSessionDataStore {

    /**
     * The username of the user.
     */
    var username: String?

    /**
     * The timestamp of the login time.
     */
    var loginTime: Long?

    /**
     * The timestamp of the logout time.
     */
    var logoutTime: Long?

    /**
     * Whether the remember me option was turned on or off.
     */
    var rememberMe: Boolean

    /**
     * The timestamp of the first login time of the entire application's history.
     */
    var firstAppLogin: Long?

    /**
     * Registers a [SharedPreferences] listener on the [LoginSessionDataStore] implementation.
     */
    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

    /**
     * Unregisters a [SharedPreferences] listener on the [LoginSessionDataStore] implementation.
     */
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener)

}