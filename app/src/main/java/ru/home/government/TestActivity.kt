package ru.home.government

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.home.government.repository.GovernmentRepository
import ru.home.government.screens.deputies.DeputiesViewModel
import javax.inject.Inject


class TestActivity: FragmentActivity() {

    @Inject
    lateinit var repo: GovernmentRepository

    fun experimentalFetcher() {
//        val lawCodes = cacheRepository.getLawCodes().toTypedArray()
/*        lifecycleScope.launch {
            repo
                .loadLawsByNumber()
                .stream(StoreRequest.fresh("1000975-7"))
                .onEach {  }
                .catch {  }
                .collect { it ->
                    Log.d(App.TAG, "Collect call: " + (it is StoreResponse.Loading));
                    Log.d(App.TAG, "Collect call: [type] " + it.toString())
                }
        }*/
    }

    @ExperimentalCoroutinesApi
    fun repeatOnLifeCycleResearch() {
/*        val viewModel = ViewModelProviders.of(this@TestActivity).get(DeputiesViewModel::class.java)
        viewModel.init(application as AppApplication)
        viewModel.fetchDeputies()*/
        Log.d(App.TAG, "[1]")
        lifecycleScope.launch {
            Log.d(App.TAG, "[2]")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d(App.TAG, "[3]")
                val viewModel = ViewModelProviders.of(this@TestActivity).get(DeputiesViewModel::class.java)
                viewModel.init(application as AppApplication)
                Log.d(App.TAG, "[7]")
//                viewModel.fetchDeputies()
                viewModel.deputies.collect { result ->
                    Log.d(App.TAG, "[4]")
                    when (result) {
/*                        is FetcherResult.Data -> {
                            Log.d(App.TAG, "[5]")
                            Log.d(App.TAG, "Result with payload: " + result.value.size)
//                            val activeDeputies = result.value.filter { it -> it.isCurrent!! }
//                            Log.d(App.TAG, "Update with amount of items: " + activeDeputies.size)
//                            (list.adapter as DeputiesAdapter).update(activeDeputies)
                        }
                        is FetcherResult.Error -> {
                            Log.d(App.TAG, "[6]")
                            Log.d(App.TAG, "Result with error")
                        }*/
                    }
                }

            }
            Log.d(App.TAG, "[8]")
        }
        Log.d(App.TAG, "[9]")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AppApplication).component.inject(this)

        repeatOnLifeCycleResearch()

//        experimentalFetcher()

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