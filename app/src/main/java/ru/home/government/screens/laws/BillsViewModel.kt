package ru.home.government.screens.laws

import androidx.lifecycle.ViewModel
import com.flatstack.android.model.network.NetworkBoundResource
import ru.home.government.AppApplication
import ru.home.government.model.GovResponse
import ru.home.government.screens.repository.GovernmentRepository
import javax.inject.Inject

class BillsViewModel: ViewModel() {

    @Inject
    lateinit var repository: GovernmentRepository

    lateinit var boundResource: NetworkBoundResource<GovResponse, GovResponse>

    fun init(application: AppApplication) {
        application.getComponent().inject(this)
        boundResource = repository.loadIntroducedLaws()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    fun fetch() {
        boundResource.fetchFromNetwork()
    }

}