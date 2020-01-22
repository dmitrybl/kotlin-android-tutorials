package ru.dmitry.belyaev.app.kotlincoroutinesexample.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.dmitry.belyaev.app.kotlincoroutinesexample.models.User

interface ApiService {

    @GET("placeholder/user/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User
}