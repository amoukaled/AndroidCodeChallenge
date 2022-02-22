package com.amoukaled.androidcodechallenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.models.Todo
import com.amoukaled.androidcodechallenge.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: TodoRepository, dispatchers: DispatcherProvider) : ViewModel() {

    private val _todoStateFlow = MutableStateFlow<List<Todo>?>(null)
    val todoStateFlow: StateFlow<List<Todo>?> = _todoStateFlow

    init {
        // mapping the flow to a nullable list
        viewModelScope.launch(dispatchers.io) {
            todoRepository.todoStateFlow.collect { flow ->
                _todoStateFlow.emit(flow.data)
            }
        }
    }

}
