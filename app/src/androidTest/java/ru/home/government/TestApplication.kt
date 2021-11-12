package ru.home.government

import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.FakeRepositoryModule
import ru.home.government.di.modules.AppModule

class TestApplication: AppApplication() {

    lateinit var fakeRepositoryModule: FakeRepositoryModule

    @JvmName("getFakeRepositoryModule1")
    fun getFakeRepositoryModule(): FakeRepositoryModule {
        return fakeRepositoryModule
    }

    override fun initializeComponent() {
        fakeRepositoryModule = FakeRepositoryModule(this)
        component = DaggerTestApplicationComponent
            .builder()
            .appModule(AppModule(this))
            .fakeRepositoryModule(fakeRepositoryModule)
            .build()
    }
}