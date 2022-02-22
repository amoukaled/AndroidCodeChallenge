package com.amoukaled.androidcodechallenge.models

/**
 * Generic api resource class
 */
sealed class ApiResource<T>(val data: T?, val message: String?) {

    /**
     * Indicates a successful api call
     * @param data The data.
     */
    class Success<T>(data: T) : ApiResource<T>(data, null)

    /**
     * Indicated an error resource.
     * @param message The error message.
     */
    class Error<T>(message: String) : ApiResource<T>(null, message)

    /**
     * Indicated loading resource.
     */
    class Loading<T> : ApiResource<T>(null, null)

}
