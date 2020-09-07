package ru.home.government.screens.deputies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.model.Deputy
import ru.home.government.repository.NewGovernmentRepository
import java.util.*
import javax.inject.Inject

class DeputiesViewModel: ViewModel() {

    @Inject
    lateinit var repository: NewGovernmentRepository

    val deputiesLiveData: MutableLiveData<FetcherResult<List<Deputy>>> = MutableLiveData<FetcherResult<List<Deputy>>>()

    fun init(application: AppApplication) {
        application.getComponent().inject(this)
        // FIXME issue with canceled coroutine job
    }

    override fun onCleared() {
        super.onCleared()
//        repository.onDestroy()

        viewModelScope
    }

    fun subscribeOnDeputies(): LiveData<FetcherResult<List<Deputy>>> {
        return deputiesLiveData
    }

    fun fetchDeputies() {
        viewModelScope.launch {
            deputiesLiveData.postValue(FetcherResult.Data(Collections.emptyList()))
            repository.loadDeputies()
                .stream(StoreRequest.fresh(0))
                .collect { result ->
                    deputiesLiveData.postValue(result.dataOrNull())
                }
        }
    }

}