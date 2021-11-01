package ru.home.government.di

import dagger.Component
import ru.home.government.TestActivity
import ru.home.government.screens.deputies.DeputiesFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.deputies.DeputiesViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(context: DeputiesViewModel)
    fun inject(context: BillsViewModel)
    fun inject(testActivity: TestActivity)

    fun inject(deputiesFragment: DeputiesFragment)
}