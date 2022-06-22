package ru.home.government.di

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import ru.home.government.di.modules.AppModule
import ru.home.government.network.MockInterceptor
import javax.inject.Singleton

@Module(includes = [])
class TestAppModule(val context: Context) {

    @Singleton
    @Provides
    fun providesContext(): Context {
        return context
    }
}