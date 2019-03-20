package ru.dmitry.belyaev.rxjavasample2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Function
import com.jakewharton.rxbinding.widget.RxTextView
import io.reactivex.functions.Predicate
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()

        compositeDisposable?.add(Observable.just("Алексей", "Иван",
            "Владимир", "Дмитрий", "Евгений", "Илья")
            .map {
                "$it 1 "
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("myLogs", it)
            })

        compositeDisposable?.add(Observable.just("Алексей", "Иван",
            "Владимир", "Дмитрий", "Евгений", "Илья")
            .flatMap {
                val delay = java.util.Random().nextInt(5)
                Observable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("myLogs", it)
            })

        compositeDisposable?.add(Observable.just("Алексей", "Иван",
            "Владимир", "Дмитрий", "Евгений", "Илья")
            .switchMap {
                val delay = java.util.Random().nextInt(5)
                Observable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("myLogs", it)
            })

        compositeDisposable?.add(Observable.just("Алексей", "Иван",
            "Владимир", "Дмитрий", "Евгений", "Илья")
            .concatMap {
                val delay = java.util.Random().nextInt(5)
                Observable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("myLogs", it)
            })


        compositeDisposable?.add(Observable.just("Алексей", "Иван",
            "Владимир", "Дмитрий", "Евгений", "Илья")
            .groupBy(object: Function<String, Any> {
                override fun apply(t: String): Boolean {
                   return t.toLowerCase().contains("и")
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                if(result.key == true) {
                    val disp = result.subscribeOn(Schedulers.newThread())
                        .subscribe { name ->
                            Log.d("myLogs", "it $name")
                        }
                }
            })

        compositeDisposable?.add(Observable.just(1,2,3,4,5,6)
            .scan(object: BiFunction<Int, Int, Int> {
                override fun apply(t1: Int, t2: Int): Int {
                    return t1 * t2
                }

            }).switchMap {
                Observable.just(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("myLogs", "result: $it")
            })

        compositeDisposable?.add(Observable.just(1,2,3,4,5,6)
            .filter {
                it % 2 == 0
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("myLogs", "result: $it")
            })


        RxTextView.textChanges(edText)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                runOnUiThread {
                    tvResult.text = it
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}
