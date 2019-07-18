package info.fandroid.top100currencies.di

import dagger.Module
import dagger.Provides
import info.fandroid.top100currencies.mvp.presenter.CurrenciesPresenter
import info.fandroid.topcrypts.mvp.presenter.LatestChartPresenter
import javax.inject.Singleton

@Module
class MvpModule {

    @Provides
    @Singleton
    fun provideCurrenciesPresenter(): CurrenciesPresenter = CurrenciesPresenter()

    @Provides
    @Singleton
    fun provideLatestChartPresenter(): LatestChartPresenter = LatestChartPresenter()
}