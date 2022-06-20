package ru.home.government.screens.deputies

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.R
import ru.home.government.TestApplication
import ru.home.government.di.*
import ru.home.government.di.fakes.FakeBillPagingSource
import ru.home.government.di.modules.AppModule
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.screens.MainActivity
import javax.inject.Inject

/**
 * From test coverage perspective this is not complete test case. Its intent
 * to serve as show case of mainstream android testing and foundation for future work.
 * */
@LargeTest
@RunWith(AndroidJUnit4::class)
class DeputiesFragmentTest {

    @Inject
    lateinit var pagingSource: FakeBillPagingSource

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        val application = appContext as TestApplication
        application.component = DaggerTestApplicationComponent
            .builder()
            .testAppModule(TestAppModule(appContext))
            .testCustomNetworkModule(TestCustomNetworkModule())
            .testRepositoryModule(TestRepositoryModule(appContext))
            .build()

        (application.component as TestApplicationComponent).inject(this)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_openDeputies_successfullyComplete() {
        pagingSource.setOkWithFullPayloadResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.navigation_home))
            .perform(ViewActions.click())

        activityScenario.close()
    }

}