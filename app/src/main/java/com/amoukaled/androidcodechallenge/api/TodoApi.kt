package com.amoukaled.androidcodechallenge.api

import com.amoukaled.androidcodechallenge.models.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    companion object {
        const val JSON_PLACE_HOLDER_URL = "https://jsonplaceholder.typicode.com"
    }

    /**
     * Gets the Todos from the api
     */
    @GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>

}