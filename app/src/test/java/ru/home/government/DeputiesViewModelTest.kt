package ru.home.government

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import ru.home.government.model.Deputy
import ru.home.government.network.IApi
import ru.home.government.repository.Response
import ru.home.government.repository.pagination.BillsPagingSource
import ru.home.government.screens.deputies.DeputiesViewModel

/**
 * From test coverage perspective this is not complete test case. Its intent
 * to serve as show case of mainstream android testing and foundation for future work.
 * */
@ExperimentalCoroutinesApi
@FlowPreview
class DeputiesViewModelTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val executor = InstantTaskExecutorRule()

    private lateinit var subject: DeputiesViewModel

    private lateinit var autoCloseable: AutoCloseable

    private lateinit var repository: FakeRepository

    private val context: Context = mock()

    private val api: IApi = mock()

    private val billsPagingSource: BillsPagingSource = mock()

    @Before
    fun setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this)

        repository = FakeRepository(context, api, billsPagingSource)

        `when`(context.getString(any()))
            .thenReturn("https://honeypot.com")
    }

    @After
    fun tearDown() {
        autoCloseable.close()
    }

    @Test
    fun onSubjectSubscribe_allOk_returnNonEmptyCollection() = mainCoroutineRule.runBlockingTest {
        lateinit var actual: Response<List<Deputy>>
        repository.getDeputies().collect { it ->
            actual = it
        }

        subject = DeputiesViewModel(repository)

        assertNotNull(subject.deputies.value)
        assertEquals((actual as Response.Data<List<Deputy>>).data.size, (subject.deputies.value as Response.Data<List<Deputy>>).data.size)
        assertFalse(subject.isLoading.value)

        subject.viewModelScope.cancel()
        mainCoroutineRule.testDispatcher.advanceUntilIdle()
    }
}