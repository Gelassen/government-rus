package ru.home.government.screens.laws.filter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

import ru.home.government.BaseApiTest
import ru.home.government.R
import ru.home.government.TestApplication
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.network.ServerErrorUtil
import ru.home.government.robots.LawScreenRobot
import ru.home.government.screens.MainActivity
import javax.inject.Inject

@Ignore
@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsFilteredFragmentTest : BaseApiTest() {

    @Inject
    lateinit var serverErrorUtil: ServerErrorUtil

    private val robot: LawScreenRobot = LawScreenRobot()

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    override fun setUp() {
        super.setUp()
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        ((InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication)
            .component as TestApplicationComponent).inject(this)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_withPositiveResponse_show1stPageOfSearchBills() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsSearchApi.setBillsSearchPositive1stPageResponse()
        dispatcher.getApiResponse().billsSearchApi.setBillsSearchPositive2stPageResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        val searchQuery = "суд"
        val whatShouldBeWithinResult = "О военных судах"
        robot.clickSearchItem()
        robot.enterSearchQuery(searchQuery)
        robot
            .seesListItems(resId = R.id.list_laws_filtered, count = 40)
            .seesListItemWithText(order = 0, text = whatShouldBeWithinResult)
            .doesNotSeeProgressIndicator()
            .doesNotSeeExpandSearchView(text = whatShouldBeWithinResult)

        activityScenario.close()
    }

    @Test
    fun onStart_withEmptyResponse_noContentShown() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsSearchApi.setBillsSearchEmptyResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        val searchQuery = "med"
        val whatShouldBeWithinResult = "med"
        robot.clickSearchItem()
        robot.enterSearchQuery(searchQuery)
        robot
            .doesNotSeeListItems(resId = R.id.list_laws_filtered)
            .doesNotSeeExpandSearchView(text = whatShouldBeWithinResult)
            .seesNoDataView(appContext)
            .doesNotSeeProgressIndicator()

        activityScenario.close()
    }

    @Test
    fun onStart_withServerError_errorViewIsShownWithNoContentIsShown() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsSearchApi.setServerErrorResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        val searchQuery = "суд"
        val whatShouldBeWithinResult = "О военных судах"
        robot.clickSearchItem()
        robot.enterSearchQuery(searchQuery)
        robot
            .doesNotSeeListItems(resId = R.id.list_laws_filtered)
            .doesNotSeeExpandSearchView(text = whatShouldBeWithinResult)
            .seesNoDataView(appContext)
            .seesErrorMessage(serverErrorUtil.getErrorMessageByServerResponseCode(500))
            .doesNotSeeProgressIndicator()

        activityScenario.close()
    }

    @Test
    fun onStart_withOkResponsePressBackButton_seesPreviousListWIthBills() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsSearchApi.setBillsSearchEmptyResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        val searchQuery = "med"
        val whatShouldBeWithinResult = "med"
        robot.clickSearchItem()
        robot.enterSearchQuery(searchQuery)
        robot
            .doesNotSeeListItems(resId = R.id.list_laws_filtered)
            .doesNotSeeExpandSearchView(text = whatShouldBeWithinResult)
            .seesNoDataView(appContext)
            .doesNotSeeProgressIndicator()
        robot.pressBackButton()
        robot.seesListItems(resId = R.id.list, 40)

        activityScenario.close()
    }

}