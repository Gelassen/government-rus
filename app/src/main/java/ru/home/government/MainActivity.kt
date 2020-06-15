package ru.home.government

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import ru.home.government.screens.model.DeputiesViewModel
import ru.home.government.screens.repository.GovernmentRepository
import ru.home.government.util.observeBy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = prepareApi()

        val repository = GovernmentRepository(this, client, ErrorHandler(Gson(), StringResource(this)))
        val viewModel = DeputiesViewModel(repository)
        viewModel.deputiesBoundResource
            .asLiveData()
            .observeBy(
                this,
                onNext = {
                    // TODO complete me
                        it -> Log.d("TAG", "Data arrived: " + it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        viewModel.fetchDeputies()
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

    private fun visibleProgress(show: Boolean) {
//        refreshLayout.isRefreshing = show
    }

    private fun showError(errorText: String?) {
        errorText?.let {
            Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
        }
    }
}
