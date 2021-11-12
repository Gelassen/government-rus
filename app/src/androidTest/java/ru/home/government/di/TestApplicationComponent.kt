package ru.home.government.di

import dagger.Component
import ru.home.government.ExampleInstrumentedTest
import ru.home.government.di.modules.AppModule
import ru.home.government.di.modules.ViewModelModule
import ru.home.government.screens.laws.main.LawsMainFragmentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [FakeRepositoryModule::class, AppModule::class, ViewModelModule::class])
interface TestApplicationComponent: AppComponent {
    fun inject(exampleInstrumentedTest: ExampleInstrumentedTest)
    fun inject(lawsMainFragmentTest: LawsMainFragmentTest)
}