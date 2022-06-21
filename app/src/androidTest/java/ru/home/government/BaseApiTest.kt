package ru.home.government

import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import ru.home.government.di.TestCustomNetworkModule
import ru.home.government.network.MockApiResponse
import ru.home.government.network.MockDispatcher

abstract class BaseApiTest {

    private lateinit var server: MockWebServer

    @Before
    open fun setUp() {
        server = MockWebServer()
        server.start()

        /**
         * Please note getInstrumentation().targetContext used for _test_ and getInstrumentation().context
         * used for _androidTest_
         *
         * @ref https://stackoverflow.com/a/35690692/17410359
         * */
        val appContext = InstrumentationRegistry.getInstrumentation().context
        MockApiResponse.DeputiesApi.setOkDeputiesResponse(appContext)
        server.setDispatcher(MockDispatcher())
        val url: HttpUrl = server.url(TestCustomNetworkModule().providesApiEndpoint())
    }

    @After
    open fun tearDown() {
        server.shutdown()
    }
}