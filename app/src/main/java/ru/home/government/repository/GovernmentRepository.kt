package ru.home.government.repository

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import ru.home.government.App
import ru.home.government.BuildConfig
import ru.home.government.R
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.model.VotesResponse
import ru.home.government.network.IApi
import ru.home.government.repository.pagination.LawsPageSource
import ru.home.government.repository.pagination.SearchLawsPageSource
import ru.home.government.util.attachIdlingResource
import java.util.*

open class GovernmentRepository(
    private val context: Context,
    private val api: IApi) {

    fun loadIntroducedLaws(): Flow<PagingData<Law>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                LawsPageSource(
                    api,
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token)
                )
            }
        ).flow
            .onStart {
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.increment()
                }
            }
            .onEach { it ->
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.decrement()
                }
            }
/*            .onCompletion {
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.decrement()
                }
            }*/
    }

    fun loadLawsByName(name: String): Flow<PagingData<Law>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                SearchLawsPageSource(
                    api,
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    name
                )
            }
        ).flow
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

    @Deprecated("Use loadVotesByLawV2() instead")
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

    // TODO move exception handling in catch block in flow, e.g. stream().catch()

    @Deprecated(message = "Use loadDeputiesV2() instead")
    @ExperimentalCoroutinesApi
    @FlowPreview
    open fun loadDeputies(): Store<Int, FetcherResult<List<Deputy>>> {
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

    open fun loadDeputiesV2(): Flow<Response<List<Deputy>>> {
        return flow {
            val response = api.getAllDeputiesV2(
                context.getString(R.string.api_key),
                context.getString(R.string.api_app_token)
            )
            if (response.isSuccessful) {
                emit(Response.Data<List<Deputy>>(response.body()!!))
            } else {
                emit(Response.Error.Message(response.message()))
            }
        }
            .onStart {
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.increment()
                }
            }
            .onCompletion {
                if (BuildConfig.DEBUG) {
                    NetworkIdlingResource.decrement()
                }
            }
    }

    open fun loadVotesByLawV2(number: String): Flow<Response<VotesResponse>> {
        return flow {
            val response = api.newGetLawVotesV2(
                context.getString(R.string.api_key),
                context.getString(R.string.api_app_token),
                number
            )
            if (response.isSuccessful) {
                emit(Response.Data<VotesResponse>(response.body()!!))
            } else {
                emit(Response.Error.Message(response.message()))
            }
        }.attachIdlingResource()
    }
}

sealed class Response<out T: Any> {
    data class Data<out T: Any>(val data: T): Response<T>()
    sealed class Error: Response<Nothing>() {
        data class Exception(val error: Throwable): Error()
        data class Message(val msg: String): Error()
    }
}