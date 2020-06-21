package ru.home.government.screens.deputies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.flatstack.android.model.entities.Resource
import com.flatstack.android.model.network.NetworkBoundResource
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.di.AppModule
import ru.home.government.model.Deputy
import ru.home.government.repository.GovernmentRepository
import javax.inject.Inject

class DeputiesViewModel: ViewModel() {

//    @Inject
    lateinit var repository: GovernmentRepository

    lateinit var deputiesBoundResource: NetworkBoundResource<List<Deputy>, List<Deputy>>

    fun init(application: AppApplication) {
//        application.getComponent().inject(this)
        val di = AppModule(application)
        // FIXME issue with canceled coroutine job
        repository = di.providesRepository(di.providesApi(di.providesClient()))
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    fun subscribeOnDeputies(): LiveData<Resource<List<Deputy>>> {
        deputiesBoundResource = repository.loadDeputies()
        return deputiesBoundResource.asLiveData()
    }

    fun fetchDeputies() {
        deputiesBoundResource.fetchFromNetwork()
    }

}