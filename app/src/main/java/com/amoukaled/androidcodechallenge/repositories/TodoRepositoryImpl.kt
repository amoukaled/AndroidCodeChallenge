package com.amoukaled.androidcodechallenge.repositories

import android.content.Context
import com.amoukaled.androidcodechallenge.R
import com.amoukaled.androidcodechallenge.api.TodoApi
import com.amoukaled.androidcodechallenge.models.ApiResource
import com.amoukaled.androidcodechallenge.models.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoRepositoryImpl(private val context: Context, private val todoApi: TodoApi) : TodoRepository {

    private val _todoStateFlow = MutableStateFlow<ApiResource<List<Todo>>>(ApiResource.Success(emptyList()))
    override val todoStateFlow: StateFlow<ApiResource<List<Todo>>>
        get() = _todoStateFlow

    // NOTE: in production, more error handling cases would be added
    // especially while using private apis with custom Exceptions and error messages.
    override suspend fun refreshTodos() {
        try {
            _todoStateFlow.emit(ApiResource.Loading())
            val response = todoApi.getTodos()

            if (!response.isSuccessful) {
                throw Exception()
            }

            val body = response.body() ?: throw Exception()
            _todoStateFlow.emit(ApiResource.Success(body))
        } catch (e: Exception) {
            _todoStateFlow.emit(ApiResource.Error(context.getString(R.string.network_error)))
        }
    }
}