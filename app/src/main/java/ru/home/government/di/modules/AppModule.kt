package ru.home.government.di.modules

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.home.government.network.IApi
import ru.home.government.R
import ru.home.government.network.PlainInterceptor
import ru.home.government.network.adapter.CustomTypeAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [CustomNetworkModule::class])
open class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun providesContext(): Context {
        return context
    }

}