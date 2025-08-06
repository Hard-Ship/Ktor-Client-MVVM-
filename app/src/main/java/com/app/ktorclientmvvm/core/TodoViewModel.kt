package com.app.ktorclientmvvm.core


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ktorclientmvvm.data.ApiService
import com.app.ktorclientmvvm.data.Todo
import com.app.ktorclientmvvm.data.TodoRepository
import com.app.ktorclientmvvm.util.showToast
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
                showToast("POST request Successful")
            }.onFailure { e->
                updateTodoScreenUiState(
                    TodoScreenUiState.Error(
                        message = e.message ?: "Error"
                    )
                )
            }
        }
    }

    fun onToggleCompleted(isCompleted : Boolean,todo: Todo) {
        viewModelScope.launch {

            val currentTodos = (_uiState.value as TodoScreenUiState.Success).todoList.toMutableList()
            val response = repository.updateTodo(todo.copy(completed = isCompleted))

            response.onSuccess { updatedTodo->

                val updatedTodos = currentTodos.map { todo ->
                    if (todo.id == updatedTodo.id) {
                        todo.copy(completed = updatedTodo.completed)
                    } else {
                        todo
                    }
                }
                updateTodoScreenUiState(
                    TodoScreenUiState.Success(
                        todoList = updatedTodos
                    )
                )
                showToast("PUT request Successful")
            }.onFailure { e->
                updateTodoScreenUiState(
                    TodoScreenUiState.Error(
                        message = e.message ?: "Error"
                    )
                )
            }
        }
    }

    fun onDeleteTodo(todo: Todo) {
        viewModelScope.launch {
            val currentTodos = (_uiState.value as TodoScreenUiState.Success).todoList
            val response = repository.deleteTodo(todo)

            response.onSuccess {
                updateTodoScreenUiState(
                    TodoScreenUiState.Success(
                        todoList = currentTodos.minus(it)
                    )
                )
                showToast("DELETE request Successful")
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