package ru.home.government

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = prepareApi()

        client.getAllDeputies(getString(R.string.api_key), getString(R.string.api_app_token))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it -> Log.d("TAG", "Data arrived: " + it)
            }
    }

    private fun prepareApi(): Api {
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
            .client(httpClient)
            .baseUrl(getString(R.string.url))
            .build()

        return retrofit.create(Api::class.java)
    }
}
