package ru.home.government.screens.model

import androidx.lifecycle.ViewModel
import ru.home.government.screens.repository.GovernmentRepository

class DeputiesViewModel(
    private val repository: GovernmentRepository
) : ViewModel() {

    val deputiesBoundResource = repository.loadDeputies()

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

    fun fetchDeputies() {
        deputiesBoundResource.fetchFromNetwork()
    }
}