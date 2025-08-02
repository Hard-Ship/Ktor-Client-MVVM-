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

}