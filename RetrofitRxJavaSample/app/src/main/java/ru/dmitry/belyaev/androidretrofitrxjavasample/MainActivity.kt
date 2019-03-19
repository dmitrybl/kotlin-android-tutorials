package ru.dmitry.belyaev.androidretrofitrxjavasample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var myAPI: RetrofitClient
    private lateinit var recycler_posts: RecyclerView
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_posts = findViewById(R.id.recycler_posts)
        recycler_posts.layoutManager = LinearLayoutManager(this)
        myAPI = RetrofitClient.create()

        fetchData()

    }

    private fun fetchData() {
        compositeDisposable.add(myAPI.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val adapter = PostsAdapter(this, it)
                recycler_posts.adapter = adapter
            }, {
                Toast.makeText(this, "Error loading data!", Toast.LENGTH_LONG).show()
            }))
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }
}
