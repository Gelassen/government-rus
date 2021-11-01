package ru.home.government.screens.deputies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.FetcherResult
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.model.Deputy
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import java.util.*
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class DeputiesViewModel
@Inject constructor(private val repository: GovernmentRepository) : ViewModel() {

    private val placeholder: Response<List<Deputy>> = Response.Data(Collections.emptyList())

    private val _deputies = MutableStateFlow(placeholder)
    val deputies: StateFlow<Response<List<Deputy>>> = _deputies

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun init(application: AppApplication) {
        application.getComponent().inject(this)
        // FIXME issue with canceled coroutine job
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    init {
        /* launch call return Job instance which should be canceled to avoid leak */
        viewModelScope.launch {
            /*this@launch.cancel() -- just test coroutine behaviour*/
            repository.loadDeputiesV2()
                /*.cancellable() -- just test coroutine behaviour*/
                .onCompletion {
                    _isLoading.value = false
                }
                .catch { e ->
                    Log.e(App.TAG, "Something went wrong on loading deputies", e)
                }
                .collect { result ->
                    _deputies.value = result
                }
        }

    }

}