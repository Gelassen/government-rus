package ru.home.government

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.flow.collect
import ru.home.government.model.Deputy
import ru.home.government.model.VotesResponse
import ru.home.government.repository.NewGovernmentRepository
import javax.inject.Inject


class TestActivity: AppCompatActivity() {

    @Inject
    lateinit var repo: NewGovernmentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AppApplication).component.inject(this)


        // DONE
/*        lifecycleScope.launchWhenCreated {
            val module = AppModule(applicationContext)
            val client = module.providesApi(module.providesClient())
            val result = client.newGetAllDeputies(
                getString(R.string.api_key),
                getString(R.string.api_app_token)
            )
            Log.d(App.TAG, "Fetcher result: " + result.toString())
        }*/

/*        lifecycleScope.launchWhenCreated {
            repo.loadDeputies()
                .stream(StoreRequest.fresh(1))
                .collect {
                    result ->
                    val result = result.dataOrNull()
                    when(result) {
                        is FetcherResult.Data -> Log.d(App.TAG, "laws by name: " + result.value.size)
                        is FetcherResult.Error.Exception -> Log.d(App.TAG, "laws by name - exception", result.error)
                        else -> {
                            Log.d(App.TAG, "Laws by number response has unsupported type")
                        }
                    }
                }
        }*/

/*        lifecycleScope.launchWhenCreated {
            repo.loadLawsByName()
                .stream(StoreRequest.fresh("мед"))
                .collect { result ->
                    val result = result.dataOrNull()
                    when(result) {
                        is FetcherResult.Data -> Log.d(App.TAG, "laws by name: " + result.value.wording + " " + result.value.count)
                        is FetcherResult.Error.Exception -> Log.d(App.TAG, "laws by name - exception", result.error)
                        else -> {
                            Log.d(App.TAG, "Laws by number response has unsupported type")
                        }
                    }
                }
        }*/

//        StrictMode.setThreadPolicy(
//            StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork() // or .detectAll() for all detectable problems
//                .penaltyDeath()
//                .build()
//        )

/*        lifecycleScope.launchWhenCreated {
            repo.loadLawsByNumber()
                .stream(StoreRequest.fresh("1000074-7"))
                .collect { result ->
                    val result = result.dataOrNull()
                    when(result) {
                        is FetcherResult.Data -> Log.d(App.TAG, "laws by number: " + result.value.wording + " " + result.value.count)
                        is FetcherResult.Error.Exception -> Log.d(App.TAG, "laws by number - exception", result.error)
                        else -> {
                            Log.d(App.TAG, "Laws by number response has unsupported type")
                        }
                    }
                }
        }*/

/*        lifecycleScope.launchWhenCreated {
            repo.loadVotesByLaw()
                .stream(StoreRequest.fresh("1001105-7"))
                .collect {
                        result ->
                    // TODO result.requireData(), it has some unexplored intent as it throws exceptions when dataOrNull returns response
                    Log.d(App.TAG, "Votes response")
                    val result = result.dataOrNull()// as FetcherResult<VotesResponse>;
                    when (result) {
                        is FetcherResult.Data -> Log.d(App.TAG, "Vote result: " + result.value.wording)
                        is FetcherResult.Error.Exception -> Log.e(App.TAG, "Vote request error: ", result.error)
                        is FetcherResult.Error.Message -> Log.d(App.TAG, "Vote request error message: " + result.message)
                        else -> {
                            Log.d(App.TAG, "Votes response has unsupported type")
                        }
                    }
                }
        }*/
    }
}