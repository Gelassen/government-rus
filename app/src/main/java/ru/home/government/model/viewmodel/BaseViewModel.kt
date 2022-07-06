package ru.home.government.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.home.government.R
import ru.home.government.repository.Response
import ru.home.government.screens.deputies.DeputiesModel
import ru.home.government.screens.deputies.Model

open class BaseViewModel : ViewModel() {

/*    private lateinit var state: MutableStateFlow<Model>
    val uiState: StateFlow<Model> = state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, state.value)

    // TODO trying to implement generic implementation, see insight on how to call copy() on interface
    //  in this thread https://stackoverflow.com/questions/47252663/kotlin-using-data-class-generics-type

    fun removeShownError() {
        state.update { state ->
            state.copy(
                errors = state.errors.filter { str -> !str.equals(state.errors.first()) }
            )
        }
    }*/

    protected fun getErrorMessage(errorResponse: Response.Error): String {
        var errorMessage = ""
        when(errorResponse) {
            is Response.Error.Exception -> { errorMessage = "Неизвестаня ошибка: \n\n${errorResponse.error.message}" }
            is Response.Error.Message -> { errorMessage = "Неизвестаня ошибка: \n\n${errorResponse.msg}" }
        }
        return errorMessage
    }

}