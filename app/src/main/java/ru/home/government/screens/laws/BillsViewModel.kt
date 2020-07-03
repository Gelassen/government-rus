package ru.home.government.screens.laws

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.flatstack.android.model.entities.Resource
import com.flatstack.android.model.network.NetworkBoundResource
import kotlinx.coroutines.flow.Flow
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.di.AppModule
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

    lateinit var boundResource: NetworkBoundResource<GovResponse, GovResponse>
    lateinit var searchLaw: NetworkBoundResource<GovResponse, GovResponse>
    lateinit var votesByLaw: NetworkBoundResource<VotesResponse, VotesResponse>

    fun init(application: AppApplication) {
        application.getComponent().inject(this)
//        val module = AppModule(application)
//        repository = module.providesRepository(module.providesApi(module.providesClient()))
        cacheRepository = CacheRepository(application)
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    fun getLaws(): Flow<PagingData<Law>> {
        return repository.loadIntroducedLaws()/*.cachedIn(viewM)*/
    }

    fun getLawsByName(name: String): Flow<PagingData<Law>> {
        // TODO on name == '' use getLaws() as search call would not work here
        return repository.loadLawsByName(name)
    }

    fun subscribeOnLawsByNumber(number: String): LiveData<Resource<GovResponse>> {
        searchLaw = repository.loadLawByNumber(number)
        return searchLaw.asLiveData()
    }

    fun fetchLawByNumber() {
        searchLaw!!.fetchFromNetwork(viewModelScope)
    }

    fun getTrackedLaws(): LiveData<Resource<GovResponse>> {
        boundResource = repository.loadLawsByNumbers()
        return boundResource.asLiveData()
    }

    fun fetchTrackedLaws() {
        val lawCodes = cacheRepository.getLawCodes()
        boundResource.fetchFromNetwork(lawCodes)
    }

    fun subscribeOnVotesByLaw(lawNumber: String): LiveData<Resource<VotesResponse>> {
        votesByLaw = repository.loadVotesByLaw(lawNumber)
        return votesByLaw.asLiveData()
    }

    fun fetchVotesByLaw() {
        votesByLaw.fetchFromNetwork(viewModelScope)
    }

}