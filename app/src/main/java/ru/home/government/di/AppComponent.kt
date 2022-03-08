package ru.home.government.di

import dagger.Component
import ru.home.government.di.modules.AppModule
import ru.home.government.di.modules.RepositoryModule
import ru.home.government.di.modules.ViewModelModule
import ru.home.government.screens.deputies.DeputiesFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.deputies.DeputiesViewModel
import ru.home.government.screens.laws.details.LawOverviewFragment
import ru.home.government.screens.laws.details.LawVotesFragment
import ru.home.government.screens.laws.filter.LawsFilteredFragment
import ru.home.government.screens.laws.main.LawsMainFragment
import ru.home.government.screens.tracker.TrackerFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(context: DeputiesViewModel)
    fun inject(context: BillsViewModel)

    fun inject(deputiesFragment: DeputiesFragment)
    fun inject(trackerFragment: TrackerFragment)
    fun inject(lawsMainFragment: LawsMainFragment)
    fun inject(lawOverviewFragment: LawOverviewFragment)
    fun inject(lawVotesFragment: LawVotesFragment)
    fun inject(lawsFilteredFragment: LawsFilteredFragment)
}