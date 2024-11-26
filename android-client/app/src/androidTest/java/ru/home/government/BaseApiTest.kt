package ru.home.government

import android.content.Context
import android.net.wifi.WifiManager
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.TestCustomNetworkModule
import ru.home.government.network.MockDispatcher


abstract class BaseApiTest {
/*
    @Inject
    @Named("Test port")
    lateinit var port: Integer*/

    protected lateinit var dispatcher: MockDispatcher

    protected lateinit var appContext: Context

    private lateinit var server: MockWebServer

    @Before
    open fun setUp() {
        // implement custom test runner https://developer.android.com/codelabs/android-dagger#13
        appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        val application = appContext as TestApplication
        (application.component as TestApplicationComponent).inject(this)

        dispatcher = MockDispatcher(appContext)
        server = MockWebServer()
        server.dispatcher = dispatcher
        server.start(TestCustomNetworkModule.Const.PORT)
    }

    @After
    open fun tearDown() {
        server.shutdown()
    }

    protected fun toggleWiFiConnection(enable: Boolean) {
        val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.isWifiEnabled = enable
    }
}