package com.amoukaled.androidcodechallenge.models

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Coroutine dispatcher provider
 */
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}