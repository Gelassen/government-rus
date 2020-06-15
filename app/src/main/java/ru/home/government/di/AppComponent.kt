package ru.home.government.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import ru.home.government.screens.model.DeputiesViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(context: DeputiesViewModel)
}