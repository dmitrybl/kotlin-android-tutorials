package info.fandroid.top100currencies.di

import dagger.Component
import info.fandroid.top100currencies.activities.MainActivity
import info.fandroid.top100currencies.fragments.CurrenciesListFragment
import info.fandroid.top100currencies.mvp.presenter.CurrenciesPresenter
import info.fandroid.topcrypts.mvp.presenter.LatestChartPresenter
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, RestModule::class, MvpModule::class, ChartModule::class))
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(fragment: CurrenciesListFragment)

    fun inject(presenter: CurrenciesPresenter)
    fun inject(presenter: LatestChartPresenter)

}