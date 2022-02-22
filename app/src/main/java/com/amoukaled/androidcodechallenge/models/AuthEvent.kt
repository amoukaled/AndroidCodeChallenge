package com.amoukaled.androidcodechallenge.models

/**
 * Authentication event resource.
 * @property username The username of the logged user.
 * @property message The error message.
 */
sealed class AuthEvent(val username: String?, val message: String?) {

    /**
     * Indicates an authenticated state.
     */
    class Authenticated(username: String) : AuthEvent(username, null)

    /**
     * Indicates an unauthenticated state
     */
    class UnAuthenticated : AuthEvent(null, null)

    /**
     * Indicates an error state
     */
    class Error(message: String) : AuthEvent(null, message)

    /**
     * Indicates a loading state
     */
    class Loading : AuthEvent(null, null)

}
