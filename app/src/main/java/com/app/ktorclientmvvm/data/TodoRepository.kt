package com.app.ktorclientmvvm.data


class TodoRepository(
    private val apiService: ApiService
) {

    suspend fun getTodos(): TodoResponse {
        return apiService.getTodos()
    }

}