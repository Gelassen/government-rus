package ru.home.government.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.home.government.R
import ru.home.government.network.PlainInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class CustomNetworkModule(val context: Context) {

    @Singleton
    @Provides
    @Named("API endpoint")
    fun providesApiEndpoint(): String {
        return context.getString(R.string.url)
    }

    @Singleton
    @Provides
    fun provideInterceptor(context: Context): Interceptor {
        return PlainInterceptor()
    }
}