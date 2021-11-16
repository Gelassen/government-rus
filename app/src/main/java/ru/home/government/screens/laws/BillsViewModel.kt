package ru.home.government.screens.laws

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.model.GovResponse
import ru.home.government.model.Law
import ru.home.government.model.VotesResponse
import ru.home.government.repository.CacheRepository
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import javax.inject.Inject

class BillsViewModel
@Inject constructor(): ViewModel() {

    @Inject
    lateinit var repository: GovernmentRepository

    @Inject
    lateinit var cacheRepository: CacheRepository

    val trackedLiveData: MutableLiveData<FetcherResult<GovResponse>> = MutableLiveData<FetcherResult<GovResponse>>()

    override fun onCleared() {
        super.onCleared()
    }

    fun getLawByNameV2(name: String): Flow<PagingData<Law>> {
        return repository
            .getLawsByNameFilter(name)
            .cachedIn(viewModelScope)
    }

    fun getLawsByPage(): Flow<PagingData<Law>> {
        return repository
            .getIntroducedLawsV2()
            .cachedIn(viewModelScope)
    }

    private var _law: MutableStateFlow<Response<GovResponse>> = MutableStateFlow(Response.Data(GovResponse()))
    val law: StateFlow<Response<GovResponse>> = _law

    fun getLawByNumber(billNumber: String) {
        viewModelScope.launch {
            repository.getLawByNumber(billNumber)
                .catch { e ->
                    Log.e(App.TAG, "Something went wrong on loading specific law", e)
                }
                .collect { result ->
                    _law.value = result
                }
        }
    }

    private val _votesResponse: MutableStateFlow<Response<VotesResponse>> = MutableStateFlow(Response.Data(VotesResponse()))
    val votesResponse: StateFlow<Response<VotesResponse>> = _votesResponse

    fun getVotesByLawV2(billNumber: String) {
        viewModelScope.launch {
            repository.getVotesByLawV2(billNumber)
                .catch { e ->
                    Log.e(App.TAG, "Something went wrong on loading deputies", e)
                }
                .collect { result ->
                    Log.d(App.TAG, "getVotesByLawV2()::collect{}")
                    _votesResponse.value = result
                }
        }
    }

    fun getTrackedLaws(): LiveData<FetcherResult<GovResponse>> {
        return trackedLiveData
    }

    @Deprecated("Replaced by flow. You have to ony subscribe on updates of StateFlow")
    fun fetchTrackedLaws() {
        val lawCodes = cacheRepository.getLawCodes().toTypedArray()
        viewModelScope.launch {
            flowOf(*lawCodes)
                .flatMapMerge { it ->
                    repository
                        .loadLawsByNumber()
                        .stream(StoreRequest.fresh(it))
                }
                .collect { it ->
                    Log.d(App.TAG, "experimentalFetcher call")
                    trackedLiveData.postValue(it.dataOrNull())
                }
        }
    }
}