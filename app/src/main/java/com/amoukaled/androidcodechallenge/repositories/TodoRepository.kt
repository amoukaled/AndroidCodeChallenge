package com.amoukaled.androidcodechallenge.repositories

import com.amoukaled.androidcodechallenge.models.ApiResource
import com.amoukaled.androidcodechallenge.models.Todo
import kotlinx.coroutines.flow.StateFlow

interface TodoRepository {

    /**
     * StateFlow holding the Resource class for the TODOs
     */
    val todoStateFlow: StateFlow<ApiResource<List<Todo>>>

    /**
     * Refreshes the in-memory Task list
     */
    suspend fun refreshTodos()

}