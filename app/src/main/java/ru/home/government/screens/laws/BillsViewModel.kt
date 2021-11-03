package ru.home.government.screens.laws

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
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

    val lawsLiveData: MutableLiveData<FetcherResult<GovResponse>> = MutableLiveData<FetcherResult<GovResponse>>()
    val votesLiveData: MutableLiveData<FetcherResult<VotesResponse>> = MutableLiveData<FetcherResult<VotesResponse>>()
    val trackedLiveData: MutableLiveData<FetcherResult<GovResponse>> = MutableLiveData<FetcherResult<GovResponse>>()

    @Deprecated(message = "All fields are initialized now via DI module")
    fun init(application: AppApplication) {
        application.getComponent().inject(this)
//        cacheRepository = CacheRepository(application)
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getLaws(): Flow<PagingData<Law>> {
        return repository.loadIntroducedLaws()
    }

    fun getLawsByName(name: String): Flow<PagingData<Law>> {
        // TODO on name == '' use getLaws() as search call would not work here
        return repository.loadLawsByName(name)
    }

    @Suppress("UNCHECKED_CAST")
    fun subscribeOnLawsByNumber(): LiveData<FetcherResult<GovResponse>> {
        return lawsLiveData
    }

    fun fetchLawByNumber(number: String) {
        viewModelScope.launch {
            repository.loadLawsByNumber()
                .stream(StoreRequest.fresh(number))
                .collect { result ->
                    lawsLiveData.postValue(result.dataOrNull())
                }
        }
    }

    fun subscribeOnVotesByLaw(): LiveData<FetcherResult<VotesResponse>> {
        return votesLiveData
    }

    @Deprecated("Use loadVotesByLawV2()")
    fun fetchVotesByLaw(lawNumber: String) {
        viewModelScope.launch {
            repository.loadVotesByLaw()
                .stream(StoreRequest.fresh(lawNumber))
                .collect{ result ->
                    votesLiveData.postValue(result.dataOrNull())
                }
        }
    }

    private val _votesResponse: MutableStateFlow<Response<VotesResponse>> = MutableStateFlow(Response.Data(VotesResponse()))
    val votesResponse: StateFlow<Response<VotesResponse>> = _votesResponse

    fun getVotesByLawV2(billNumber: String) {
        viewModelScope.launch {
            repository.loadVotesByLawV2(billNumber)
                .catch { e ->
                    Log.e(App.TAG, "Something went wrong on loading deputies", e)
                }
                .collect { result ->
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