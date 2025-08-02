package com.app.ktorclientmvvm.core


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ktorclientmvvm.data.ApiService
import com.app.ktorclientmvvm.data.Todo
import com.app.ktorclientmvvm.data.TodoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repository =
        TodoRepository(ApiService())

    private val _uiState =
        MutableStateFlow<TodoScreenUiState>(TodoScreenUiState.Loading)

    val uiState: StateFlow<TodoScreenUiState> = _uiState

    fun updateTodoScreenUiState(state: TodoScreenUiState) {
        _uiState.value = state
    }

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            delay(1000)
            val response = repository.getTodos()

            response.onSuccess {
                updateTodoScreenUiState(
                    TodoScreenUiState.Success(
                        todoList = it.todos
                    )
                )
            }.onFailure { e->
                updateTodoScreenUiState(
                    TodoScreenUiState.Error(
                        message = e.message ?: "Error"
                    )
                )
            }

        }
    }

    fun onAddTodo() {
        viewModelScope.launch {
            val currentTodos = (_uiState.value as TodoScreenUiState.Success).todoList
            val response = repository.addTodo(Todo(
                id = 0,
                todo = "Successfully did a POST Request",
                completed = false,
                userId = 1
            ))

            response.onSuccess {
                updateTodoScreenUiState(
                    TodoScreenUiState.Success(
                        todoList = currentTodos.plus(it)
                    )
                )
            }.onFailure { e->
                updateTodoScreenUiState(
                    TodoScreenUiState.Error(
                        message = e.message ?: "Error"
                    )
                )
            }
        }
    }
}