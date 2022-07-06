package ru.home.government.screens.laws

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.model.dto.GovResponse
import ru.home.government.model.dto.Law
import ru.home.government.model.dto.VotesResponse
import ru.home.government.model.viewmodel.BaseViewModel
import ru.home.government.repository.CacheRepository
import ru.home.government.repository.GovernmentRepository
import ru.home.government.repository.Response
import ru.home.government.screens.deputies.Model
import javax.inject.Inject

data class BillsModel(
    override var isLoading: Boolean = false,
    override val errors: List<String> = emptyList(),
    var billsByName: PagingData<Law> = PagingData.empty(),
    var billsByPage: PagingData<Law> = PagingData.empty(),
    var billsByNumber: GovResponse = GovResponse(),
    var votesByLaw: VotesResponse = VotesResponse(),
    var billsWhichTracked: GovResponse = GovResponse()
) : Model
class BillsViewModel
@Inject constructor(): BaseViewModel() {

    @Inject
    lateinit var repository: GovernmentRepository

    @Inject
    lateinit var cacheRepository: CacheRepository

    private val state: MutableStateFlow<BillsModel> = MutableStateFlow(BillsModel())
    val uiState: StateFlow<BillsModel> = state
        .asStateFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, state.value)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun getLawByNameV2(name: String) {
        viewModelScope.launch {
            repository
                .getLawsByNameFilter(name)
                .cachedIn(viewModelScope)
                .onStart { state.update { state -> state.copy(isLoading = true) } }
                .collect { result ->
                    state.update { state -> state.copy(billsByName = result, isLoading = false) }
                }
        }
    }

    fun getLawsByPageV2() {
        viewModelScope.launch {
            repository
                .getIntroducedLaws()
                .cachedIn(viewModelScope)
                .onStart { state.update { state -> state.copy(isLoading = false) } }
                .collect { result ->
                    state.update { state -> state.copy(billsByPage = result, isLoading = false) }
                }
        }
    }

    fun getLawByNumberV2(billNumber: String) {
        viewModelScope.launch {
            repository.getLawByNumber(billNumber)
                .onStart { state.update { state -> state.copy(isLoading = false) } }
                .collect { result ->
                    when(result) {
                        is Response.Data -> { state.update { state -> state.copy(billsByNumber = result.data, isLoading = false) } }
                        is Response.Error -> { state.update { state -> state.copy(errors = state.errors.plus(getErrorMessage(result)), isLoading = false) } }
                    }
                }
        }
    }

    fun getVotesByLawV2(billNumber: String) {
        viewModelScope.launch {
            repository.getVotesByLaw(billNumber)
                .onStart { state.update { state -> state.copy(isLoading = false) } }
                .collect { result ->
                    when(result) {
                        is Response.Data -> { state.update { state -> state.copy(votesByLaw = result.data, isLoading = false) } }
                        is Response.Error -> { state.update { state -> state.copy(errors = state.errors.plus(getErrorMessage(result)), isLoading = false) } }
                    }
                }
        }
    }

    private val _trackedLaws: MutableStateFlow<Response<GovResponse>>
        = MutableStateFlow(Response.Data(GovResponse()))
    val trackedLaws: StateFlow<Response<GovResponse>> = _trackedLaws

    @OptIn(FlowPreview::class)
    fun fetchedTrackedLaws() {
        val lawCodes = cacheRepository.lawCodes.toTypedArray()
        viewModelScope.launch {
            flowOf(*lawCodes)
                .flatMapMerge { it ->
                    repository.getLawByNumber(it)
                }
                .collect { it ->
                    _trackedLaws.value = it
                }
        }
    }

    fun addError(error: String) {
        state.update { state ->
            state.copy(errors = state.errors.plus(error))
        }
    }

    fun removeShownError() {
        state.update { state ->
            state.copy(errors = state.errors.filter { str ->
                !str.equals(state.errors.first()) })
        }
    }
}