package ru.dmitry.belyaev.app.kotlincoroutinesexample

import ru.dmitry.belyaev.app.kotlincoroutinesexample.models.User

sealed class Status {
    class Loading: Status()
    class Error(val message: String): Status()
    class Success(val result: User): Status()
}

