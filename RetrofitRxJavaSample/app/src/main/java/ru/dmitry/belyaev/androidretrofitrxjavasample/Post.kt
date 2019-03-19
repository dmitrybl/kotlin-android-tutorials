package ru.dmitry.belyaev.androidretrofitrxjavasample

import com.google.gson.annotations.SerializedName

data class Post(@SerializedName("userId") val uid: Int,
                @SerializedName("id") val id: Int,
                @SerializedName("title") val title: String,
                @SerializedName("body") val content: String)