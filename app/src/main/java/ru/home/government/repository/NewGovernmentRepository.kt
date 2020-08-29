package ru.home.government.repository

import android.content.Context
import android.util.Log
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.flatstack.android.model.network.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.home.government.App
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.VotesResponse
import ru.home.government.network.IApi
import java.util.*

class NewGovernmentRepository(
    private val context: Context,
    private val api: IApi) {

    private lateinit var scope: CoroutineScope

    fun setScope(scope: CoroutineScope) {
        this.scope = scope
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @FlowPreview
    fun getData(token: String, appToken: String): Store<Int, FetcherResult<GovResponse>> {
        val result = StoreBuilder
            .from(Fetcher.of { page: Int ->
                api.newGetIntroducedLaws(
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    page)
            })
            .build()
        return result
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadVotesByLaw(): Store<String, FetcherResult<VotesResponse>> {
        return StoreBuilder
            .from(Fetcher.of { number: String ->
                var result: FetcherResult<VotesResponse> = FetcherResult.Data(VotesResponse())
                try {
                    result = api.newGetLawVotes(
                        context.getString(R.string.api_key),
                        context.getString(R.string.api_app_token),
                        number
                    )
                } catch (ex: Exception) {
                    result = FetcherResult.Error.Exception(ex)
                }
                result
            })
            .build()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadLawsByName(): Store<String, FetcherResult<GovResponse>> {
        return StoreBuilder
            .from(
                Fetcher.of { name: String ->
                    var result: FetcherResult<GovResponse> = FetcherResult.Data(GovResponse())
                    try {
                        result = api.newGetLawByName(
                            context.getString(R.string.api_key),
                            context.getString(R.string.api_app_token),
                            1,
                            name
                        )
                    } catch (ex: Exception) {
                        Log.e(App.TAG, "Failed to execute API call", ex)
                        result = FetcherResult.Error.Exception(ex)
                    }
                    result
                }
            ).build()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadLawsByNumber(): Store<String, FetcherResult<GovResponse>> {
        return StoreBuilder
            .from(
                Fetcher.of { number: String ->
                    var result: FetcherResult<GovResponse> = FetcherResult.Data(GovResponse())
                    try {
                        result = api.newGetLawByNumber(
                            context.getString(R.string.api_key),
                            context.getString(R.string.api_app_token),
                            number
                        )
                    } catch (ex: Exception) {
                        result = FetcherResult.Error.Exception(ex)
                    }
                    result
                }
            ).build()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadDeputies(): Store<Int, FetcherResult<List<Deputy>>> {
        return StoreBuilder
            .from(
                Fetcher.of { key: Int ->
                    var result: FetcherResult<List<Deputy>> = FetcherResult.Data(Collections.emptyList<Deputy>())
                    try {
                        result = api.newGetAllDeputies(
                            context.getString(R.string.api_key),
                            context.getString(R.string.api_app_token)
                        )
                    } catch (ex: Exception) {
                        Log.e(App.TAG, "Failed to process result", ex);
                        result = FetcherResult.Error.Exception(ex)
                    }
                    result
                }
            ).build()
    }
}