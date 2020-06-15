package ru.home.government.screens.model

import androidx.lifecycle.ViewModel
import com.flatstack.android.model.network.NetworkBoundResource
import ru.home.government.AppApplication
import ru.home.government.model.Deputy
import ru.home.government.screens.repository.GovernmentRepository
import javax.inject.Inject

class DeputiesViewModel: ViewModel() {

    @Inject
    lateinit var repository: GovernmentRepository

    lateinit var deputiesBoundResource: NetworkBoundResource<List<Deputy>, List<Deputy>>


    fun init(application: AppApplication) {
        application.getComponent().inject(this)
        deputiesBoundResource = repository.loadDeputies()
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    fun fetchDeputies() {
        deputiesBoundResource.fetchFromNetwork()
    }

}