package com.flatstack.android.model.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flatstack.android.model.entities.Resource
import com.flatstack.android.model.network.errors.ErrorHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.R
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import kotlin.coroutines.CoroutineContext

abstract class NetworkBoundResource<ResultType, RequestType>(
        override val coroutineContext: CoroutineContext,
        private val errorHandler: ErrorHandler
) : CoroutineScope {

    private val result = MutableLiveData<Resource<ResultType>>()

    init {
        result.postValue(Resource.loading())
//        fetchFromNetwork()
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    fun fetchFromNetwork(scope: CoroutineScope) {
        scope.launch {
            fetchFromNetwork()
        }
    }

    fun fetchFromNetwork(codes: Set<String>) {
        launch {
            val result = GovResponse()
            for (code in codes) {
                when (val apiResponse = createCallAsync(code)!!.await()) {
                    is ApiSuccessResponse -> {
                        result.laws.addAll((apiResponse.body as GovResponse).laws)
                    }
                    is ApiErrorResponse -> {
                        // no op
                    }
                }
            }
            this@NetworkBoundResource.result.postValue(Resource.success(result as ResultType))
        }
    }

    suspend fun fetchFromNetwork() {
        try {
            if (isSkipCache()) {
                when (val apiResponse = createCallAsync().await()) {
                    is ApiSuccessResponse -> {
                        result.postValue(Resource.success(apiResponse.body as ResultType))
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed()
                        result.postValue(Resource.error(errorHandler.proceed(apiResponse.error), loadFromDb()))
                    }
                }
            } else {
                result.postValue(Resource.loading(loadFromDb()))
                when (val apiResponse = createCallAsync().await()) {
                    is ApiSuccessResponse -> {
                        saveCallResult(processResponse(apiResponse))
                        result.postValue(Resource.success(loadFromDb()))
                    }
                    is ApiErrorResponse -> {
                        onFetchFailed()
                        result.postValue(Resource.error(errorHandler.proceed(apiResponse.error), loadFromDb()))
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(App.TAG, "Failed to process data", ex)
        }
    }

    protected open fun isSkipCache(): Boolean {
        return true
    }

    protected open fun onFetchFailed() {}

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract suspend fun saveCallResult(item: RequestType)

    protected abstract suspend fun loadFromDb(): ResultType?

    protected abstract suspend fun createCallAsync(): Deferred<ApiResponse<RequestType>>

    protected open suspend fun createCallAsync(code: String): Deferred<ApiResponse<RequestType>>? { return null}
}
