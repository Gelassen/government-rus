package ru.home.government.di.modules

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.home.government.network.IApi
import ru.home.government.network.adapter.CustomTypeAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule() {

    @Singleton
    @Provides
    fun providesApi(httpClient: OkHttpClient, @Named("API endpoint") uri: String): IApi {
        val customGson = GsonBuilder().registerTypeAdapterFactory(CustomTypeAdapterFactory()).create()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .client(httpClient)
            .baseUrl(uri)
            .build()

        return retrofit.create(IApi::class.java)
    }

    @Singleton
    @Provides
    fun providesClient(interceptor: Interceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .build()

        return httpClient
    }
}