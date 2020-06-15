package ru.home.government.di

import dagger.Component
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.model.DeputiesViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(context: DeputiesViewModel)
    fun inject(context: BillsViewModel)
}