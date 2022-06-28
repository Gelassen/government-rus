package ru.home.government.screens.laws.filter

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

import ru.home.government.BaseApiTest
import ru.home.government.R
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.robots.LawScreenRobot
import ru.home.government.screens.MainActivity

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsFilteredFragmentTest : BaseApiTest() {

    private val robot: LawScreenRobot = LawScreenRobot()

    private val dataBindingIdlingResource = DataBindingIdlingResource()

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
    @Ignore
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
            .seesListItems(40)
            .seesListItemWithText(order = 0, text = whatShouldBeWithinResult)
            .doesNotSeeProgressIndicator()
            .doesNotSeeExpandSearchView(text = whatShouldBeWithinResult)

        activityScenario.close()
    }


    @Test
    @Ignore
    fun onStart_withEnterSearchWordForPositiveCase_showsTwoItems() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot.clickSearchItem()
        robot.enterSearchQuery("medicine")
        robot.seesListItems(R.id.list_laws_filtered,2)

        activityScenario.close()
    }

    // TODO on positive case returns both pages with bills, progress indicator is hidden, no expanded search view in toolbar
    // TODO on empty search returns empty response, no content shown, progress indicator is hidden, no expanded search view in toolbar
    // TODO on negative case error view is shown, no content shown, progress indicator is hidden, no expanded search view in toolbar
    // TODO on back button main fragment with origin list of bills shown, no expanded search view in toolbar
}