package ru.home.government.di

import android.content.Context
import com.flatstack.android.model.network.errors.ErrorHandler
import com.flatstack.android.util.StringResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.home.government.network.IApi
import ru.home.government.R
import ru.home.government.network.adapter.CoroutineCallAdapterFactory
import ru.home.government.network.adapter.CustomTypeAdapterFactory
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.NewGovernmentRepository
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {

    @Singleton
    @Provides
    fun providesApi(httpClient: OkHttpClient): IApi {
        val customGson = GsonBuilder().registerTypeAdapterFactory(CustomTypeAdapterFactory()).create()
        val retrofit = Retrofit.Builder()
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(customGson/*GsonBuilder().create()*/))
//            .addConverterFactory(CustomGsonConverterFactory.create(customGson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
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
    fun providesRepository(client: IApi): GovernmentRepository {
        return GovernmentRepository(context, client, ErrorHandler(Gson(), StringResource(context)))
    }

    @Singleton
    @Provides
    fun providesNewRepository(client: IApi): NewGovernmentRepository {
        return NewGovernmentRepository(context, client)
    }
}