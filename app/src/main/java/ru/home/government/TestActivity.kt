package ru.home.government

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dropbox.android.external.store4.StoreRequest
import com.flatstack.android.model.network.errors.ErrorHandler
import com.flatstack.android.util.StringResource
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import ru.home.government.di.AppModule
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.NewGovernmentRepository
import javax.inject.Inject

class TestActivity: AppCompatActivity() {

    @Inject
    lateinit var repo: NewGovernmentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AppApplication).component.inject(this)

//        lifecycleScope.launchWhenCreated {
//            repo.getData(
//                getString(R.string.api_key),
//                getString(R.string.api_app_token)
//            )
//                .stream(StoreRequest.fresh(1))
//                .collect { result ->
//                    Log.d(App.TAG, "repo.getData() result call")
//                    Log.d(App.TAG, "result: " + result.dataOrNull().toString())
//                    result.throwIfError()
//                }
//        }

        // check chain of adapters is not broke
/*        lifecycleScope.launchWhenCreated {
            val module = AppModule(applicationContext)
            val client = module.providesApi(module.providesClient())
            GovernmentRepository(
                applicationContext,
                client,
                ErrorHandler(Gson(), StringResource(applicationContext)))
                .loadDeputies()
                .fetchFromNetwork()
        }*/


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

        lifecycleScope.launchWhenCreated {
            repo.loadDeputies()
                .stream(StoreRequest.fresh(1))
                .collect {
                    result ->
                    Log.d(App.TAG, "Deputies are loaded: " + result.dataOrNull().toString());
                    Log.d(App.TAG, "Error message if any: " + result.errorMessageOrNull());
                }
        }

//        lifecycleScope.launchWhenCreated {
//            repo.loadLawsByName("мед")
//                .stream(StoreRequest.fresh("мед"))
//                .collect {
//                        result ->
//                    Log.d(App.TAG, "Laws by name are loaded: " + result.dataOrNull().toString())
//                }
//        }

//        lifecycleScope.launchWhenCreated {
//            repo.loadLawsByNumber("1000074-7")
//                .stream(StoreRequest.fresh("1000074-7"))
//                .collect {
//                        result ->
//                    Log.d(App.TAG, "Laws by number are loaded: " + result.dataOrNull().toString())
//                }
//        }

//        lifecycleScope.launchWhenCreated {
//            repo.loadVotesByLaw("1001105-7")
//                .stream(StoreRequest.skipMemory("1001105-7", false))
//                .collect {
//                        result ->
//                    Log.d(App.TAG, "Votes by number are loaded: " + result.dataOrNull().toString().length)
//                }
//        }
    }
}