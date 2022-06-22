package ru.home.government

import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.TestCustomNetworkModule
import ru.home.government.di.modules.AppModule
import ru.home.government.di.modules.RepositoryModule

class TestApplication: AppApplication() {

    override fun initializeComponent() {
        component = DaggerTestApplicationComponent
            .builder()
            .appModule(AppModule(this))
            .repositoryModule(RepositoryModule(this))
            .testCustomNetworkModule(TestCustomNetworkModule())
            .build()
    }
}