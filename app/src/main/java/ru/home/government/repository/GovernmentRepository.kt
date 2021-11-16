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
import ru.home.government.repository.pagination.BillsPagingSource
import ru.home.government.repository.pagination.SearchLawsPagingSource
import ru.home.government.util.attachIdlingResource

private const val DEFAULT_PAGE_SIZE = 20

open class GovernmentRepository(
    private val context: Context,
    private val api: IApi,
    private val billsPagingSource: BillsPagingSource
) {

    open fun getIntroducedLawsV2(): Flow<PagingData<Law>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                billsPagingSource
            }
        )
            .flow
            .attachIdlingResource()
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

    fun getLawsByNameFilter(filter: String): Flow<PagingData<Law>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                SearchLawsPagingSource(
                    api,
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    filter
                )
            }
        )
            .flow
            .attachIdlingResource()
    }

    open fun getLawByNumber(billNumber: String): Flow<Response<GovResponse>> {
        return flow {
            val response = api.getLawByNumberV2(
                context.getString(R.string.api_key),
                context.getString(R.string.api_app_token),
                billNumber
            )
            if (response.isSuccessful) {
                emit(Response.Data(response.body()!!))
            } else {
                emit(Response.Error.Message(response.message()))
            }
        }
            .catch { ex ->
                Response.Error.Exception(ex)
            }
            .attachIdlingResource()
    }

    open fun getDeputiesV2(): Flow<Response<List<Deputy>>> {
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
            .catch { ex ->
                Response.Error.Exception(ex)
            }
            .attachIdlingResource()
    }

    open fun getVotesByLawV2(number: String): Flow<Response<VotesResponse>> {
        return flow {
            Log.d(App.TAG, "[start] getVotesByLawV2()")
            val response = api.newGetLawVotesV2(
                context.getString(R.string.api_key),
                context.getString(R.string.api_app_token),
                number
            )
            Log.d(App.TAG, "getVotesByLawV2() - get response ${response.isSuccessful}")
            if (response.isSuccessful) {
                emit(Response.Data<VotesResponse>(response.body()!!))
            } else {
                emit(Response.Error.Message(response.message()))
            }
            Log.d(App.TAG, "[end] getVotesByLawV2()")
        }
            .catch { ex ->
                Response.Error.Exception(ex)
            }
            .attachIdlingResource()
    }
}

sealed class Response<out T: Any> {
    data class Data<out T: Any>(val data: T): Response<T>()
    sealed class Error: Response<Nothing>() {
        data class Exception(val error: Throwable): Error()
        data class Message(val msg: String): Error()
    }
}