package ru.home.government.di

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import ru.home.government.App
import ru.home.government.R
import ru.home.government.di.fakes.FakeBillPagingSource
import ru.home.government.di.fakes.FakeRepository
import ru.home.government.di.modules.AppModule
import ru.home.government.network.IApi
import ru.home.government.repository.CacheRepository
import ru.home.government.repository.GovernmentRepository
import javax.inject.Singleton

@Module(includes = [AppModule::class])
open class FakeRepositoryModule(
    val context: Context
) {

    @Singleton
    @Provides
    open fun providesBillsPagingSource(client: IApi): FakeBillPagingSource {
        Log.d(App.TEST, "FakeRepositoryModule::providesBillsPagingSource()")
        return FakeBillPagingSource(
            client,
            context.getString(R.string.api_key),
            context.getString(R.string.api_app_token)
        )
    }

    @Singleton
    @Provides
    fun providesNewRepository(client: IApi, billsPagingSource: FakeBillPagingSource): GovernmentRepository {
        Log.d(App.TEST, "FakeRepositoryModule::providesNewRepository()")
        return FakeRepository(context, client, billsPagingSource)
    }

    @Provides
    fun providesCacheRepository(): CacheRepository {
        return CacheRepository(context)
    }

}