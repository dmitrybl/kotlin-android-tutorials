package ru.dmitry.belyaev.app.kotlincoroutinesexample.models

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("username")
    val username: String? = null,

    @SerializedName("image")
    val image: String? = null

) {
    override fun toString(): String {
        return "User(email=$email, username=$username, image=$image)"
    }
}