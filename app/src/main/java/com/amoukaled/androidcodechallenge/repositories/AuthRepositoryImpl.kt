package com.amoukaled.androidcodechallenge.repositories

import android.content.Context
import android.content.SharedPreferences
import com.amoukaled.androidcodechallenge.R
import com.amoukaled.androidcodechallenge.data.dao.UserDao
import com.amoukaled.androidcodechallenge.data.dataStore.LoginSessionDataStore
import com.amoukaled.androidcodechallenge.data.dataStore.TimerDataStore
import com.amoukaled.androidcodechallenge.data.entities.User
import com.amoukaled.androidcodechallenge.models.AuthEvent
import com.amoukaled.androidcodechallenge.utils.BcryptUtils
import com.amoukaled.androidcodechallenge.utils.CustomDateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepositoryImpl(
    private val loginDataStore: LoginSessionDataStore,
    private val userDao: UserDao,
    private val context: Context,
    private val timerDataStore: TimerDataStore
) : AuthRepository {

    // Listener that triggers to refresh the auth state flow.
    private val loginListener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
        updateFromSessionStore()
    }

    private val _authStateFlow: MutableStateFlow<AuthEvent> = MutableStateFlow(AuthEvent.UnAuthenticated())
    override val authStateFlow: StateFlow<AuthEvent>
        get() = _authStateFlow

    override suspend fun loginUser(inputUsername: String, inputPassword: String, inputRememberMe: Boolean) {
        _authStateFlow.emit(AuthEvent.Loading())

        val user = userDao.findByUsername(inputUsername)
        user?.let { _ ->
            // validate password and take if true
            BcryptUtils.validate(user.password, inputPassword).takeIf { it }?.let { _ ->
                with(loginDataStore) {
                    val now = CustomDateUtils.now()

                    // update data store
                    username = inputUsername
                    rememberMe = inputRememberMe
                    loginTime = now

                    // reset logout time
                    logoutTime = null

                    // if first time logging in, save the timestamp
                    if (firstAppLogin == null) {
                        firstAppLogin = now
                    }
                    return
                }
            }
        }

        _authStateFlow.emit(AuthEvent.Error(context.getString(R.string.incorrect_credentials)))
    }

    /**
     * Checks the [LoginSessionDataStore.rememberMe] and [LoginSessionDataStore.logoutTime] and
     * signs the user out according to the persisted state.
     * @note This is used on app init to synchronize the login state, since a proper app shutdown cannot be guaranteed.
     */
    override suspend fun checkRememberMe() {
        if (!loginDataStore.rememberMe && loginDataStore.logoutTime == null) {
            logoutUser()
        }
    }

    override suspend fun logoutUser() {
        loginDataStore.logoutTime = CustomDateUtils.now()
        timerDataStore.clear()
    }

    /**
     * Synchronizes the auth state by calling [checkRememberMe].
     * Initializes the [_authStateFlow] value.
     * Registers [loginListener] on [LoginSessionDataStore] to dynamically login and out the user.
     */
    override suspend fun startup() {
        checkRememberMe()
        updateFromSessionStore()
        loginDataStore.registerListener(loginListener)
    }

    /**
     * Registers the [loginListener].
     * @note Used when resuming the activity.
     */
    override fun resume() {
        loginDataStore.registerListener(loginListener)
    }

    /**
     * Unregisters the [loginListener].
     * @note Used when pausing or destroying the activity to prevent memory leaks.
     */
    override fun pause() {
        loginDataStore.unregisterListener(loginListener)
    }

    @Suppress("SpellCheckingInspection")
    override suspend fun debug() {
        if (userDao.getRowCount() == 0) {
            val user1 = User("user1", "First User", "\$2a\$12\$GmnP91vMw0zhRnRkW/fs8.PGLX9Ge4Qvqu4JbmlB/JT69tZtxXvaa") // password: password1
            val user2 = User("user2", "Second User", "\$2a\$12\$jWeQn8JMXXRZzGRU.Jvbz.9GMS.kGYMhYyUUNx8Cn7OwDxSI7HQLO")// password: password2
            userDao.insertAll(listOf(user1, user2))
        }
    }

    /**
     * Updates the [_authStateFlow] value.
     */
    private fun updateFromSessionStore() {
        val state: AuthEvent = loginDataStore.run {
            username?.let { nUsername ->
                loginTime?.let { _ ->
                    return@run if (logoutTime == null) {
                        AuthEvent.Authenticated(nUsername)
                    } else {
                        null
                    }
                }
            }
        } ?: AuthEvent.UnAuthenticated()

        _authStateFlow.value = state
    }

    /**
     * Returns the login session username.
     */
    override val username: String?
        get() = loginDataStore.username

    /**
     * Returns the first app login.
     */
    override val firstLogin: Long?
        get() = loginDataStore.firstAppLogin
}