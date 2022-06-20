package ru.home.government.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.home.government.R
import ru.home.government.di.modules.NetworkModule
import ru.home.government.network.MockInterceptor
import ru.home.government.network.PlainInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class TestCustomNetworkModule {

    @Singleton
    @Provides
    @Named("API endpoint")
    fun providesApiEndpoint(): String {
        return "https://testapi.com"
    }

    @Singleton
    @Provides
    fun provideInterceptor(context: Context): Interceptor {
        return MockInterceptor()
    }
}