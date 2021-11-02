package ru.home.government.di

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.home.government.network.IApi
import ru.home.government.R
import ru.home.government.network.adapter.CustomTypeAdapterFactory
import ru.home.government.repository.CacheRepository
import ru.home.government.repository.GovernmentRepository
import javax.inject.Singleton

@Module
open class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun providesApi(httpClient: OkHttpClient): IApi {
        val customGson = GsonBuilder().registerTypeAdapterFactory(CustomTypeAdapterFactory()).create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .client(httpClient)
            .baseUrl(context.getString(R.string.url))
            .build()

        return retrofit.create(IApi::class.java)
    }

    @Singleton
    @Provides
    fun providesClient(): OkHttpClient {
         val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .build()

        return httpClient
    }

    @Singleton
    @Provides
    open fun providesNewRepository(client: IApi): GovernmentRepository {
        return GovernmentRepository(context, client)
    }

    @Provides
    fun providesCacheRepository(): CacheRepository {
        return CacheRepository(context)
    }
}