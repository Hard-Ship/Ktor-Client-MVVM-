package com.app.ktorclientmvvm.core

import com.app.ktorclientmvvm.data.Todo

sealed class TodoScreenUiState {

    object Loading : TodoScreenUiState()
    data class Error(val message: String) : TodoScreenUiState()
    data class Success(
        val todoList: List<Todo>
    ) : TodoScreenUiState()

}