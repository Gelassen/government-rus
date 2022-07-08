package ru.home.government.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.home.government.di.modules.NetworkModule
import ru.home.government.network.PlainInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class TestCustomNetworkModule {

    object Const {
        const val PORT: Int = 3100
    }

/*    @Singleton
    @Provides
    @Named("Test port")
    fun providesServerTestPort(): Integer {
        return Integer(3100)
    }*/

    @Singleton
    @Provides
    @Named("API endpoint")
    fun providesApiEndpoint(): String {
        val testApiEndpoint = "http://127.0.0.1"
        return "${testApiEndpoint}:${Const.PORT}"
    }

    @Singleton
    @Provides
    fun provideInterceptor(context: Context): Interceptor {
        return PlainInterceptor()
    }
}