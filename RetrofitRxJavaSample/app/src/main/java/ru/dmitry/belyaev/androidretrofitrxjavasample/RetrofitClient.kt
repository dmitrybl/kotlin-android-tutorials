package ru.dmitry.belyaev.androidretrofitrxjavasample

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitClient  {

    @GET("posts")
    fun getPosts(): Observable<List<Post>>

    companion object {
        fun create(): RetrofitClient {
            val retrofit =  Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://jsonplaceholder.typicode.com")
                .build()

            return retrofit.create(RetrofitClient::class.java)
        }
    }
}