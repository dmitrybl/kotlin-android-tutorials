package ru.dmitry.belyaev.rxjavasample3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var container: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = CompositeDisposable()

        val first = Observable.just("a", "b", "c")
        val second = Observable.just("1", "2", "3")


        container.add(first.zipWith(second, object: BiFunction<String, String, String> {
            override fun apply(t1: String, t2: String): String {
                return "$t1 $t2"
            }
        }).subscribe {
            Log.d("myLogs", it)
        })


        container.add(first.zipWith(Observable.interval(3000, TimeUnit.MILLISECONDS),
            object: BiFunction<String, Any, String> {
                override fun apply(t1: String, t2: Any): String {
                    return t1
                }
        }).mergeWith(second.zipWith(Observable.interval(1000, TimeUnit.MILLISECONDS),
            object: BiFunction<String, Any, String> {
                override fun apply(t1: String, t2: Any): String {
                    return t1
                }
        })).subscribe {
            Log.d("myLogs", it)
        })


        container.add(first.zipWith(Observable.interval(3000, TimeUnit.MILLISECONDS),
            object: BiFunction<String, Any, String> {
                override fun apply(t1: String, t2: Any): String {
                    return t1
                }
            }).concatWith(second.zipWith(Observable.interval(1000, TimeUnit.MILLISECONDS),
            object: BiFunction<String, Any, String> {
                override fun apply(t1: String, t2: Any): String {
                    return t1
                }
            })).subscribe {
            Log.d("myLogs", it)
        })


        val temperatureFactoryOne = Observable.just(120, 111, 90, 100, 102)
        val temperatureFactorySecond = Observable.just(-10, -20, -5, -30)

        container.add(Observable.combineLatest(temperatureFactoryOne.zipWith(Observable.interval(1000, TimeUnit.MILLISECONDS),
            BiFunction<Int, Any, Int> { t1, _ -> t1 }), temperatureFactorySecond.zipWith(Observable.interval(2000, TimeUnit.MILLISECONDS),
            BiFunction<Int, Any, Int> { t1, _ -> t1 }), object: BiFunction<Int, Int, Array<Int>> {
            override fun apply(t1: Int, t2: Int): Array<Int> {
                return arrayOf(t1, t2)
            }
        }).subscribe {
            Log.d("myLogs", "${it[0]} ${it[1]}")
        })

    }
}
