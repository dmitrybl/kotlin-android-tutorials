package ru.dmitry.belyaev.kotlinexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.dmitry.belyaev.kotlinexample.data.SearchRepository
import ru.dmitry.belyaev.kotlinexample.data.SearchRepositoryProvider
import ru.dmitry.belyaev.kotlinexample.data.SourceOfQuotes

const val tag:String = "myLogs"

class MainActivity : AppCompatActivity(), ChangeSourceListener {
    override fun sourceChanged(position: Int) {
        val intent = Intent(applicationContext, QuotesActivity::class.java)
        intent.putExtra(INTENT_NAME_NAME, adapter[position].name)
        intent.putExtra(INTENT_SITE_NAME, adapter[position].site)
        startActivity(intent)
    }

    @BindView(R.id.list)
    lateinit var listView: RecyclerView

    val compositeDisposable:CompositeDisposable = CompositeDisposable();
    val repository:SearchRepository = SearchRepositoryProvider.provideSearchRepository();
    lateinit var adapter: SourceOfQuotesAdapter

    private val list: MutableList<SourceOfQuotes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        ButterKnife.bind(this)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm
        compositeDisposable.add(repository.searchSourcesOfQuotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    result.forEach {
                        list.addAll(it)
                    }
                    adapter = SourceOfQuotesAdapter(list)
                    adapter.addListener(this)
                    listView.adapter = adapter
                    Log.d(tag, list.toString())
                })
        )
    }
}
