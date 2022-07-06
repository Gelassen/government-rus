package ru.home.government.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.home.government.R
import ru.home.government.repository.Response
import ru.home.government.screens.deputies.DeputiesModel
import ru.home.government.screens.deputies.Model

open class BaseViewModel : ViewModel() {

    protected fun getErrorMessage(errorResponse: Response.Error): String {
        var errorMessage = ""
        when(errorResponse) {
            is Response.Error.Exception -> { errorMessage = "Неизвестаня ошибка: \n\n${errorResponse.error.message}" }
            is Response.Error.Message -> { errorMessage = "Неизвестаня ошибка: \n\n${errorResponse.msg}" }
        }
        return errorMessage
    }

}