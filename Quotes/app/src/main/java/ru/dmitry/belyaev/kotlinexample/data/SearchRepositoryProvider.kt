package ru.dmitry.belyaev.kotlinexample.data

/**
 * Created by dmitrybelyaev on 22.10.2018.
 */

object SearchRepositoryProvider {

    fun provideSearchRepository():SearchRepository {
        return SearchRepository(BashImApiService.Factory.create())
    }
}