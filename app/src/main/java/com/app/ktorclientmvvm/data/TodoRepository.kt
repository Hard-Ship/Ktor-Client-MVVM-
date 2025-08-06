package com.app.ktorclientmvvm.data


class TodoRepository(
    private val apiService: ApiService
) {

    suspend fun getTodos(): Result<TodoResponse> {
        return apiService.getTodos()
    }

    suspend fun addTodo(todo: Todo): Result<Todo> {
        return apiService.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo): Result<Todo> {
        return apiService.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo): Result<Todo> {
        return apiService.deleteTodo(todo)
    }

}