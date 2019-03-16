package ru.dmitry.belyaev.rxjavatest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable = Observable.just(1,2,3)

        val disposable = observable.subscribe {
            Log.d("myLogs", "$it")
        }

        btn = findViewById(R.id.btn)
        btn.setOnClickListener {
            Log.d("myLogs", "click")
        }

        val disposable2 = dataSource()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                btn.text = "Click $it"
                Log.d("myLogs", "Disposable2 : $it")
            },{

            },{

            })

        val disposable3 = dataSource2()
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                Log.d("myLogs", "Disposable3 : $it")
            },{

            })

    }

    fun dataSource(): Flowable<Int> {
        return Flowable.create({ subscriber ->
            for(i in 0..100) {
                Thread.sleep(1000)
                subscriber.onNext(i)
            }
            subscriber.onComplete()

        }, BackpressureStrategy.MISSING)
    }

    fun dataSource2(): Single<List<Int>> {
        val list = mutableListOf(1,2,3,4,5,6,7,8,9)
        return Single.create { subscriber ->
            subscriber.onSuccess(list)
        }
    }
}
