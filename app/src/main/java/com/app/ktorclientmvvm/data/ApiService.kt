package com.app.ktorclientmvvm.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.utils.io.InternalAPI

class ApiService {

    private val TAG = "ApiService"

    // GET
    suspend fun getTodos(): Result<TodoResponse> {
        try {
            val response = HttpClientProvider.client
                .get("todos") {
                    parameter("limit", 5)
                }
            return if (response.status.isSuccess()) {
                Result.success(response.body<TodoResponse>())
            } else {
                Result.failure(Exception("Server returned status: ${response.status}"))
            }

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


    // Post
    @OptIn(InternalAPI::class)
    suspend fun addTodo(
        todo: Todo
    ): Result<Todo> {
        try {
            val response = HttpClientProvider.client.post("todos/add") {
                contentType(ContentType.Application.Json)
                setBody(
                    """{
            "todo": "${todo.todo}",
            "completed": ${todo.completed},
            "userId": ${todo.userId}
        }""".trimIndent()
                )
            }


            return if (response.status.isSuccess()) {
                Result.success(response.body<Todo>())
            } else {
                Result.failure(Exception("Server returned status: ${response.status}\n Message : ${response.bodyAsText()}"))
            }

        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

    // PUT
    @OptIn(InternalAPI::class)
    suspend fun updateTodo(
        todo: Todo
    ): Result<Todo> {
        try {
            val response = HttpClientProvider.client.put("todos/${todo.id}") {
                contentType(ContentType.Application.Json)
                setBody(
                    """{
            "todo": "${todo.todo}",
            "completed": ${todo.completed},
            "userId": ${todo.userId}
        }""".trimIndent()
                )
            }


            return if (response.status.isSuccess()) {
                Result.success(response.body<Todo>())
            } else {
                Result.failure(Exception("Server returned status: ${response.status}\n Message : ${response.bodyAsText()}"))
            }

        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

    // DELETE
    @OptIn(InternalAPI::class)
    suspend fun deleteTodo(
        todo: Todo
    ): Result<Todo> {
        try {
            val response = HttpClientProvider.client.delete("todos/${todo.id}")

            return if (response.status.isSuccess()) {
                Result.success(response.body<Todo>())
            } else {
                Result.failure(Exception("Server returned status: ${response.status}\n Message : ${response.bodyAsText()}"))
            }

        } catch (e: Exception) {
            return Result.failure(e)
        }


    }

}