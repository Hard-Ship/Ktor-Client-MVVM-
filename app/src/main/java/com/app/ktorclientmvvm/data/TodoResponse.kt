package com.app.ktorclientmvvm.data

import kotlinx.serialization.Serializable

@Serializable
data class TodoResponse(
    val todos: List<Todo>,
    val total: Int,
    val skip: Int,
    val limit: Int
)