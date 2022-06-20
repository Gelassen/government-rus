package ru.home.government.di

import dagger.Component
import ru.home.government.ExampleInstrumentedTest
import ru.home.government.di.modules.AppModule
import ru.home.government.di.modules.ViewModelModule
import ru.home.government.screens.deputies.DeputiesFragmentTest
import ru.home.government.screens.laws.LawsFragmentTest
import ru.home.government.screens.laws.details.LawDetailsFragmentTest
import ru.home.government.screens.laws.main.LawsMainFragmentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [TestRepositoryModule::class, TestAppModule::class, ViewModelModule::class])
interface TestApplicationComponent: AppComponent {
    fun inject(subject: ExampleInstrumentedTest)
    fun inject(subject: LawsMainFragmentTest)
    fun inject(subject: DeputiesFragmentTest)
    fun inject(subject: LawDetailsFragmentTest)
    fun inject(subject: LawsFragmentTest)
}