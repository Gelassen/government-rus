package ru.home.government

import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.TestAppModule
import ru.home.government.di.TestCustomNetworkModule
import ru.home.government.di.TestRepositoryModule
import ru.home.government.di.modules.AppModule

class TestApplication: AppApplication() {

    lateinit var testRepositoryModule: TestRepositoryModule

    @JvmName("getFakeRepositoryModule1")
    fun getFakeRepositoryModule(): TestRepositoryModule {
        return testRepositoryModule
    }

    override fun initializeComponent() {
        testRepositoryModule = TestRepositoryModule(this)
        component = DaggerTestApplicationComponent
            .builder()
            .testAppModule(TestAppModule(this))
            .testCustomNetworkModule(TestCustomNetworkModule())
            .testRepositoryModule(testRepositoryModule)
            .build()
    }
}