package ru.home.government

import com.dropbox.android.external.store4.FetcherResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import ru.home.government.model.dto.Deputy
import ru.home.government.repository.GovernmentRepository
import ru.home.government.screens.deputies.DeputiesViewModel
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    lateinit var subject: DeputiesViewModel

    lateinit var closeable: AutoCloseable

    @Mock lateinit var repository: GovernmentRepository

//    private val fetcher: FetcherResult<List<Deputy>> = mock()
//    private val sample: ISample = mock(ISample::class.java)
    private val fetcher: FetcherResult<List<Deputy>> = mock()

    @Before
    fun setUp() {
        closeable = MockitoAnnotations.openMocks(this)

        subject = DeputiesViewModel(
            repository
        )
    }

    @After
    fun tearDown() {
        closeable.close()
    }

    @Test
    fun onDeputiesCall_okCase_getNonEmptyCollection() {
//        org.mockito.
//        Mockito.`when`(subject.deputies).thenReturn(Collections.emptyList<>())
//        Mockito.`when`(subject.deputies).thenReturn()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
