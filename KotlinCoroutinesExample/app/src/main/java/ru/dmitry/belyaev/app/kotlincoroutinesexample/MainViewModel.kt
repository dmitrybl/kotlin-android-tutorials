package ru.dmitry.belyaev.app.kotlincoroutinesexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.dmitry.belyaev.app.kotlincoroutinesexample.api.MyRetrofitBuilder
import java.lang.Exception


class MainViewModel : ViewModel() {

    val data = MutableLiveData<Status>()

    init {
        refresh()
    }

    fun refresh() {
        data.postValue(Status.Loading())

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeout(3000) {
                    val user = MyRetrofitBuilder.apiService.getUser("1")
                    withContext(Dispatchers.Main) {
                        data.postValue(Status.Success(user))
                    }
                }
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    data.postValue(Status.Error(e.toString()))
                }
            }
        }
    }

}