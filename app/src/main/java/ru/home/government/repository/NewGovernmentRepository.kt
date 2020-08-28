package ru.home.government.repository

import android.content.Context
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.VotesResponse
import ru.home.government.network.IApi

class NewGovernmentRepository(
    private val context: Context,
    private val api: IApi) {

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

//        val result: Store<Int, GovResponse> = StoreBuilder
//            .from(Fetcher.of{ (page as Int) -> api.getIntroducedLawsNew(token, appToken, page) })
//            .build()


//        val result: Store<Int, ApiResponse<GovResponse>> = StoreBuilder
//            .from(Fetcher.ofFlow<Int, ApiResponse<GovResponse>> { page -> api.getIntroducedLawsNew(token, appToken, page) })
//            .build()
        return result
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadVotesByLaw(number: String): Store<String, FetcherResult<VotesResponse>> {
        return StoreBuilder
            .from(Fetcher.of { number: String ->
                api.newGetLawVotes(
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    number
                )
            })
            .build()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadLawsByName(name: String): Store<String, FetcherResult<GovResponse>> {
        return StoreBuilder
            .from(
                Fetcher.of { name: String ->
                    api.newGetLawByName(
                        context.getString(R.string.api_key),
                        context.getString(R.string.api_app_token),
                        1,
                        name
                    )
                }
            ).build()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadLawsByNumber(number: String): Store<String, FetcherResult<GovResponse>> {
        return StoreBuilder
            .from(
                Fetcher.of { numberShadow: String ->
                    api.newGetLawByNumber(
                        context.getString(R.string.api_key),
                        context.getString(R.string.api_app_token),
                        number
                    )
                }
            ).build()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun loadDeputies(): Store<Int, FetcherResult<List<Deputy>>> {
        return StoreBuilder
            .from(
                Fetcher.of { key: Int ->
                    api.newGetAllDeputies(
                        context.getString(R.string.api_key),
                        context.getString(R.string.api_app_token)
                    )
                }
            ).build()
    }
}