package ru.home.government.screens.deputies

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.model.dto.Deputy
import ru.home.government.model.viewmodel.BaseViewModel
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import javax.inject.Inject

interface Model {
    var isLoading: Boolean
    val errors: List<String>
}

data class DeputiesModel(
    override var isLoading: Boolean = false,
    override val errors: List<String> = emptyList<String>(),
    var deputies: List<Deputy> = emptyList()
) : Model

@FlowPreview
@ExperimentalCoroutinesApi
class DeputiesViewModel
@Inject constructor(private val repository: GovernmentRepository) : BaseViewModel() {

    private val state: MutableStateFlow<DeputiesModel> = MutableStateFlow(DeputiesModel())
    val uiState: StateFlow<DeputiesModel> = state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, state.value)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun fetchDeputies() {
        /* launch call return Job instance which should be canceled to avoid leak */
        viewModelScope.launch {
            /*this@launch.cancel() -- just test coroutine behaviour*/
            repository.getDeputies()
                .onStart { state.update { state -> state.copy(isLoading = true) } }
                /*.cancellable() -- just test coroutine behaviour*/
                .onCompletion { state.update { state -> state.copy(isLoading = false    ) } }
                .catch { e ->
                    val errorMsg = "Something went wrong on loading deputies"
                    Log.e(App.TAG, errorMsg, e)
                    state.update { state -> state.copy(errors = state.errors.plus(errorMsg), isLoading = false) }
                }
                .collect { result ->
                    when(result) {
                        is Response.Data -> {
                            state.update { state -> state.copy(deputies = result.data.filter { it.isCurrent == true }, isLoading = false)}
                        }
                        is Response.Error -> {
                            val msg = getErrorMessage(result)
                            Log.e(App.TAG, "Got an error on deputies collect: $msg")
                            state.update { state -> state.copy(errors = state.errors.plus(msg), isLoading = false) }
                        }
                    }
                }
        }
    }

    fun manuallyUpdateDeputies(deputies: List<Deputy>) {
        state.update { state ->
            state.copy(deputies = deputies)
        }
    }

    fun removeShownError() {
        state.update { state ->
            state.copy(errors = state.errors.filter { str ->
                !str.equals(state.errors.first()) })
        }
    }

}