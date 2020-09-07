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
import javax.inject.Inject

class BillsViewModel: ViewModel() {

    @Inject
    lateinit var repository: GovernmentRepository

    lateinit var cacheRepository: CacheRepository

    val lawsLiveData: MutableLiveData<FetcherResult<GovResponse>> = MutableLiveData<FetcherResult<GovResponse>>()
    val votesLiveData: MutableLiveData<FetcherResult<VotesResponse>> = MutableLiveData<FetcherResult<VotesResponse>>()
    val trackedLiveData: MutableLiveData<FetcherResult<GovResponse>> = MutableLiveData<FetcherResult<GovResponse>>()

    fun init(application: AppApplication) {
        application.getComponent().inject(this)
        cacheRepository = CacheRepository(application)
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

    fun fetchVotesByLaw(lawNumber: String) {
        viewModelScope.launch {
            repository.loadVotesByLaw()
                .stream(StoreRequest.fresh(lawNumber))
                .collect{ result ->
                    votesLiveData.postValue(result.dataOrNull())
                }
        }
    }

    fun getTrackedLaws(): LiveData<FetcherResult<GovResponse>> {
        return trackedLiveData
    }

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