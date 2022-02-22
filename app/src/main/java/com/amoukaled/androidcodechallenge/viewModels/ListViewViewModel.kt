package com.amoukaled.androidcodechallenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoukaled.androidcodechallenge.models.ApiResource
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.models.Todo
import com.amoukaled.androidcodechallenge.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewViewModel @Inject constructor(private val todoRepository: TodoRepository, private val dispatchers: DispatcherProvider) : ViewModel() {

    val todoStateFlow: StateFlow<ApiResource<List<Todo>>> = todoRepository.todoStateFlow

    /**
     * @see [TodoRepository.refreshTodos]
     */
    fun refreshTodos() {
        viewModelScope.launch(dispatchers.io) {
            todoRepository.refreshTodos()
        }
    }

}