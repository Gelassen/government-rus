package ru.home.government.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import ru.home.government.R
import ru.home.government.model.dto.Deputy
import ru.home.government.model.dto.GovResponse
import ru.home.government.model.dto.Law
import ru.home.government.model.dto.VotesResponse
import ru.home.government.network.IApi
import ru.home.government.network.ServerErrorUtil
import ru.home.government.repository.pagination.BillsPagingSource
import ru.home.government.repository.pagination.SearchLawsPagingSource
import ru.home.government.util.attachIdlingResource


private const val DEFAULT_PAGE_SIZE = 20

open class GovernmentRepository(
    private val context: Context,
    private val api: IApi,
    private val billsPagingSource: BillsPagingSource
) {

    open fun getIntroducedLaws(): Flow<PagingData<Law>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                initialLoadSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                billsPagingSource
            }
        )
            .flow
            .attachIdlingResource()
    }

    fun getLawsByNameFilter(filter: String): Flow<PagingData<Law>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                initialLoadSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchLawsPagingSource(
                    api,
                    ServerErrorUtil(context),
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    filter
                )
            }
        )
            .flow
//            .attachIdlingResource()
    }

    open fun getLawByNumber(billNumber: String): Flow<Response<GovResponse>> {
        return flow {
            val response = api.getLawByNumber(
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

    open fun getDeputies(): Flow<Response<List<Deputy>>> {
        return flow {
            if (isConnected()) {
                val response = api.getAllDeputies(
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token)
                )
                if (response.isSuccessful) {
                    emit(Response.Data<List<Deputy>>(response.body()!!))
                } else {
                    emit(Response.Error.Message(response.message()))
                }
            } else {
                emit(Response.Error.Message(context.getString(R.string.no_internet_connection_error)))
            }
        }
            .catch { ex ->
                Response.Error.Exception(ex)
            }
            .attachIdlingResource()

    }

    open fun getVotesByLaw(number: String): Flow<Response<VotesResponse>> {
        return flow {
            val response = api.newGetLawVotes(
                context.getString(R.string.api_key),
                context.getString(R.string.api_app_token),
                number
            )
            if (response.isSuccessful) {
                emit(Response.Data<VotesResponse>(response.body()!!))
            } else {
                emit(Response.Error.Message(response.message()))
            }
        }
            .catch { ex ->
                Response.Error.Exception(ex)
            }
            .attachIdlingResource()
    }

    private fun isConnected(): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

}

sealed class Response<out T: Any> {
    data class Data<out T: Any>(val data: T): Response<T>()
    sealed class Error: Response<Nothing>() {
        data class Exception(val error: Throwable): Error()
        data class Message(val msg: String): Error()
    }
}