package ru.home.government.screens.deputies

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.BaseApiTest
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.robots.DeputiesRobot
import ru.home.government.screens.MainActivity

/**
 * From test coverage perspective this is not complete test case. Its intent
 * to serve as show case of mainstream android testing and foundation for future work.
 * */
@LargeTest
@RunWith(AndroidJUnit4::class)
class DeputiesFragmentTest: BaseApiTest() {

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val deputiesRobot: DeputiesRobot = DeputiesRobot()

    @Before
    override fun setUp() {
        super.setUp()
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_openDeputies_successfullyComplete() {
        dispatcher.getApiResponse().deputiesApi.setOkDeputiesResponse()
        dispatcher.getApiResponse().billsApi.setOkBillsResponse()
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()

        activityScenario.close()
    }

    @Test
    fun onOpenDeputies_withOkResponse_seesDeputiesItems() {
        dispatcher.getApiResponse().deputiesApi.setOkDeputiesResponse()
        dispatcher.getApiResponse().billsApi.setOkBillsResponse()
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()
        deputiesRobot
            .seesShimmerIsUnveiled()
            .seesListItems(count = 50, isShimmer = true)
            .doesNotSeeNoContent()

        activityScenario.close()
    }

    @Test
    fun onOpenDeputies_withErrorResponse_seesErrorMessageAndNoContentView() {
        dispatcher.getApiResponse().deputiesApi.setServerErrorResponse()
        dispatcher.getApiResponse().billsApi.setOkBillsResponse()
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()
        deputiesRobot
            .seesShimmerIsUnveiled()
            .seesListItems(count = 0, isShimmer = true)
            .seesNoContent()
            .seesErrorMessage(appContext)

        activityScenario.close()
    }

    @Test
    fun onOpenDeputies_withOKButEmptyResponse_seesNoContentView() {
        dispatcher.getApiResponse().deputiesApi.setOkWithNoDeputiesResponse()
        dispatcher.getApiResponse().billsApi.setOkBillsResponse()
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()
        deputiesRobot
            .seesShimmerIsUnveiled()
            .seesListItems(count = 0, isShimmer = true)
            .seesNoContent()

        activityScenario.close()
    }

    @Test
    fun onOpenDeputies_withOkButBrokenResponse_seesAllContent() {
        dispatcher.getApiResponse().deputiesApi.setOkWithBrokenDeputiesResponse()
        dispatcher.getApiResponse().billsApi.setOkBillsResponse()
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()
        deputiesRobot
            .seesShimmerIsUnveiled()
            .seesListItems(count = 49, isShimmer = true)
            .doesNotSeeNoContent()

        activityScenario.close()
    }

    @Test
    fun onOpenDeputies_withOkButInvalidJsonInResponse_seesAllContent() {
        dispatcher.getApiResponse().deputiesApi.setOkWithInvalidDeputiesJsonInResponse()
        dispatcher.getApiResponse().billsApi.setOkBillsResponse()
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()
        deputiesRobot
            .seesShimmerIsUnveiled()
            .seesNoContent()
            .doesNotSeeListItems(isShimmer = true)
            .seesErrorMessage(appContext)

        activityScenario.close()
    }

    @Test
    @Ignore
    fun onOpenDeputies_withNoNetworkConnection_seesNoContentViewWithErrorMessageView() {
        // TODO find a way to disable\enable internet connection from tests, since Android 10 there is
        // no way to do it programmatically without user involvement
        toggleWiFiConnection(enable = false)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)
        dispatcher.getApiResponse().deputiesApi.setOkDeputiesResponse()

        deputiesRobot
            .seesNavView()
            .clickDeputiesTab()
        deputiesRobot
            .seesListItems(0)
            .seesNoContent()
            .doesNotSeeProgressIndicator()
            .seesErrorMessage(appContext)

        activityScenario.close()
    }

}