package ru.home.government.di

import dagger.Component
import ru.home.government.BaseApiTest
import ru.home.government.di.modules.RepositoryModule
import ru.home.government.di.modules.ViewModelModule
import ru.home.government.screens.deputies.DeputiesFragmentTest
import ru.home.government.screens.laws.LawsFragmentTest
import ru.home.government.screens.laws.details.LawDetailsFragmentTest
import ru.home.government.screens.laws.filter.LawsFilteredFragmentTest
import ru.home.government.screens.laws.main.LawsMainFragmentTest
import javax.inject.Singleton

@Singleton
@Component(modules = [TestCustomNetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface TestApplicationComponent: AppComponent {
    fun inject(subject: LawsMainFragmentTest)
    fun inject(subject: DeputiesFragmentTest)
    fun inject(subject: LawDetailsFragmentTest)
    fun inject(subject: LawsFragmentTest)
    fun inject(subject: BaseApiTest)
    fun inject(subject: LawsFilteredFragmentTest)
}