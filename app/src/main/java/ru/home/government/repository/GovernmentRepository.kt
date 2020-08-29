package ru.home.government.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.flatstack.android.model.network.ApiResponse
import com.flatstack.android.model.network.NetworkBoundResource
import com.flatstack.android.model.network.errors.ErrorHandler
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import ru.home.government.network.IApi
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.model.VotesResponse
import ru.home.government.repository.pagination.LawsPageSource
import ru.home.government.repository.pagination.SearchLawsPageSource

class GovernmentRepository(
    private val context: Context,
    private val api: IApi,
    private val errorHandler: ErrorHandler
) : CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.IO

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

    fun loadVotesByLaw(number: String): NetworkBoundResource<VotesResponse, VotesResponse> {
        return object: NetworkBoundResource<VotesResponse, VotesResponse>(coroutineContext, errorHandler) {

            override suspend fun saveCallResult(item: VotesResponse) {
                // no op
            }

            override suspend fun loadFromDb(): VotesResponse? {
                // no op
                return VotesResponse()
            }

            override suspend fun createCallAsync(): Deferred<ApiResponse<VotesResponse>> {
                return api.getLawVotes(
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    number
                )
            }
        }
    }

    @Deprecated(message = "Redundant")
    fun loadLawsByNames(name: String) =
        object: NetworkBoundResource<GovResponse, GovResponse>(coroutineContext, errorHandler) {

            override suspend fun saveCallResult(item: GovResponse) {
                // no op
            }

            override suspend fun loadFromDb(): GovResponse? {
                // no op
                return GovResponse()
            }

            override suspend fun createCallAsync(): Deferred<ApiResponse<GovResponse>> {
                return api.getLawByName(
                    context.getString(R.string.api_key),
                    context.getString(R.string.api_app_token),
                    1,
                    name
                )
            }

        }

    fun loadLawByNumber(number: String) =
        object : NetworkBoundResource<GovResponse, GovResponse>(coroutineContext, errorHandler) {
            override suspend fun createCallAsync(): Deferred<ApiResponse<GovResponse>> {
                return api.getLawByNumber(context.getString(R.string.api_key), context.getString(R.string.api_app_token), number)
            }

            override suspend fun saveCallResult(item: GovResponse) {
                // TODO("Not yet implemented")
            }

            override suspend fun loadFromDb(): GovResponse {
                // TODO("Not yet implemented")
                return GovResponse()
            }
        }

    fun loadLawsByNumbers() =
        object : NetworkBoundResource<GovResponse, GovResponse>(coroutineContext, errorHandler) {
            override suspend fun createCallAsync(): Deferred<ApiResponse<GovResponse>> {
                // stub
                return api.getLawByNumber(context.getString(R.string.api_key), context.getString(R.string.api_app_token), "")
            }

            override suspend fun saveCallResult(item: GovResponse) {
                // TODO("Not yet implemented")
            }

            override suspend fun loadFromDb(): GovResponse {
                // TODO("Not yet implemented")
                return GovResponse()
            }

            override suspend fun createCallAsync(code: String): Deferred<ApiResponse<GovResponse>> {
                return api.getLawByNumber(context.getString(R.string.api_key), context.getString(R.string.api_app_token), code)
            }
        }

    fun loadDeputies() =
        object : NetworkBoundResource<List<Deputy>, List<Deputy>>(coroutineContext, errorHandler) {
            override suspend fun createCallAsync(): Deferred<ApiResponse<List<Deputy>>> {
                return api.getAllDeputies(context.getString(R.string.api_key), context.getString(R.string.api_app_token))
            }

            override suspend fun saveCallResult(item: List<Deputy>) {
                // TODO("Not yet implemented")
            }

            override suspend fun loadFromDb(): List<Deputy>? {
                // TODO("Not yet implemented")
                return emptyList()
            }
        }

    fun onDestroy() {
        coroutineContext.cancelChildren()
    }

}