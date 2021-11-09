package ru.home.government.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.home.government.R
import ru.home.government.network.IApi
import ru.home.government.repository.CacheRepository
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.pagination.BillsPagingSource
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RepositoryModule(val context: Context) {

    @Singleton
    @Provides
    fun providesBillsPagingSource(client: IApi): BillsPagingSource {
        return BillsPagingSource(
            client,
            context.getString(R.string.api_key),
            context.getString(R.string.api_app_token)
        )
    }

    @Singleton
    @Provides
    open fun providesNewRepository(client: IApi, pagingSource: BillsPagingSource): GovernmentRepository {
        return GovernmentRepository(context, client, pagingSource)
    }

    @Provides
    fun providesCacheRepository(): CacheRepository {
        return CacheRepository(context)
    }
}