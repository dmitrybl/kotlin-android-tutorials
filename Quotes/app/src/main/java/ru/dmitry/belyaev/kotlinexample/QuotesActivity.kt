package ru.dmitry.belyaev.kotlinexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.dmitry.belyaev.kotlinexample.data.Quote
import ru.dmitry.belyaev.kotlinexample.data.SearchRepository
import ru.dmitry.belyaev.kotlinexample.data.SearchRepositoryProvider
import ru.dmitry.belyaev.kotlinexample.data.SourceOfQuotes

/**
 * Created by dmitrybelyaev on 22.10.2018.
 */

const val INTENT_NAME_NAME = "name";
const val INTENT_SITE_NAME = "site";

class QuotesActivity : AppCompatActivity() {


    @BindView(R.id.list)
    lateinit var listView: RecyclerView

    val compositeDisposable: CompositeDisposable = CompositeDisposable();
    val repository: SearchRepository = SearchRepositoryProvider.provideSearchRepository();
    lateinit var adapter: SourceOfQuotesAdapter

    private val list: MutableList<Quote> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        ButterKnife.bind(this)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm

        val site = intent.getStringExtra(INTENT_SITE_NAME)
        val name = intent.getStringExtra(INTENT_NAME_NAME)

        compositeDisposable.add(repository.searchQuotes(site, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    result.forEach {
                        list.addAll(result)
                    }
                    listView.adapter = QuotesAdapter(list)
                    Log.d(tag, list.toString())
                })
        )
    }
}
