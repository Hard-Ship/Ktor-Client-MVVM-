package com.app.ktorclientmvvm.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiService {

    suspend fun getTodos(): TodoResponse {
        return HttpClientProvider.client
            .get("todos"){
                parameter("limit", 100)
            }.body<TodoResponse>()

    }

}