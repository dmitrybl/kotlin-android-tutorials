package ru.dmitry.belyaev.kotlinexample.data

/**
 * Created by dmitrybelyaev on 22.10.2018.
 */

class SearchRepository(val apiService: BashImApiService) {

    fun searchQuotes(site: String, name: String): io.reactivex.Observable<List<Quote>> {
        return apiService.searchQuotes(site, name, 50)
    }

    fun searchSourcesOfQuotes(): io.reactivex.Observable<List<List<SourceOfQuotes>>> {
        return apiService.searchSources()
    }
}