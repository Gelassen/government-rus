package ru.home.government

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.flatstack.android.model.network.errors.ErrorHandler
import com.flatstack.android.util.StringResource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.home.government.network.adapter.CoroutineCallAdapterFactory
import ru.home.government.screens.repository.GovernmentRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = prepareApi()

        val repository = GovernmentRepository(this, client, ErrorHandler(Gson(), StringResource(this)))
        repository.loadDeputies()
            .asLiveData()
            .observe(
                this,
                Observer
                {
                        it -> Log.d("TAG", "Deputies: " + it)
                }
            )

//        client.getAllDeputies(getString(R.string.api_key), getString(R.string.api_app_token))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                it -> Log.d("TAG", "Data arrived: " + it)
//            }
    }

    private fun prepareApi(): IApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient
            .Builder()
//            .addInterceptor(ConnectionInterceptor(context, networkUtils))
            .addInterceptor(logging)
            .build()

        val customGson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .baseUrl(getString(R.string.url))
            .build()

        return retrofit.create(IApi::class.java)
    }
}
