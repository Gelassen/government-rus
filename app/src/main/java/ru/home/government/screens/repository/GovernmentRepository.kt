package ru.home.government.screens.repository

import android.content.Context
import com.flatstack.android.model.network.ApiResponse
import com.flatstack.android.model.network.NetworkBoundResource
import com.flatstack.android.model.network.errors.ErrorHandler
import kotlinx.coroutines.*
import ru.home.government.network.IApi
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.model.GovResponse

class GovernmentRepository(
    private val context: Context,
    private val api: IApi,
    private val errorHandler: ErrorHandler
) : CoroutineScope {

    override val coroutineContext = SupervisorJob() + Dispatchers.IO

    fun loadIntroducedLaws() =
        object : NetworkBoundResource<GovResponse, GovResponse>(coroutineContext, errorHandler) {
            override suspend fun createCallAsync(): Deferred<ApiResponse<GovResponse>> {
                return api.getIntroducedLaws(context.getString(R.string.api_key), context.getString(R.string.api_app_token))
            }

            override suspend fun saveCallResult(item: GovResponse) {
                // TODO("Not yet implemented")
            }

            override suspend fun loadFromDb(): GovResponse? {
                // TODO("Not yet implemented")
                return GovResponse()
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