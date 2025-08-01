package com.app.ktorclientmvvm.core


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ktorclientmvvm.data.ApiService
import com.app.ktorclientmvvm.data.Todo
import com.app.ktorclientmvvm.data.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repository =
        TodoRepository(ApiService())

    private val _todos =
        MutableStateFlow<List<Todo>>(emptyList())

    val todos: StateFlow<List<Todo>> = _todos

    init {
        loadTodos()
    }

    private fun loadTodos() {

        viewModelScope.launch {

            val response = repository.getTodos()

            _todos.value = response.todos
        }
    }
}