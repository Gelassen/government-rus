package ru.home.government.screens.laws

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.flatstack.android.model.entities.Resource
import com.flatstack.android.model.network.NetworkBoundResource
import ru.home.government.AppApplication
import ru.home.government.model.GovResponse
import ru.home.government.repository.GovernmentRepository
import javax.inject.Inject

class BillsViewModel: ViewModel() {

    @Inject
    lateinit var repository: GovernmentRepository

    lateinit var boundResource: NetworkBoundResource<GovResponse, GovResponse>
    lateinit var searchLaw: NetworkBoundResource<GovResponse, GovResponse>

    fun init(application: AppApplication) {
        application.getComponent().inject(this)

    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    fun subscribeOnLaws(): LiveData<Resource<GovResponse>> {
        boundResource = repository.loadIntroducedLaws()
        return boundResource.asLiveData()
    }

    fun fetchLaws() {
        boundResource!!.fetchFromNetwork()
    }

    fun subscribeOnLawsByNumber(number: String): LiveData<Resource<GovResponse>> {
        searchLaw = repository.loadLawByNumber(number)
        return searchLaw.asLiveData()
    }

    fun fetchLawByNumber() {
        searchLaw!!.fetchFromNetwork()
    }

}