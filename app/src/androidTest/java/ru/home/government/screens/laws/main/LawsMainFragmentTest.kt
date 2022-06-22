package ru.home.government.screens.laws.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import ru.home.government.BaseApiTest
import ru.home.government.TestApplication
import ru.home.government.di.*
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.network.ServerErrorUtil
import ru.home.government.robots.LawScreenRobot
import ru.home.government.screens.MainActivity
import ru.home.government.screens.laws.details.DetailsActivity
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsMainFragmentTest: BaseApiTest() {

    @Inject
    lateinit var serverErrorUtil: ServerErrorUtil

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val robot = LawScreenRobot()

    @Before
    override fun setUp() {
        super.setUp()
        ((InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication)
            .component as TestApplicationComponent).inject(this)

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
    fun onStart_withPositiveResponseFromServer_showsAllData() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesListViewComponentIsVisible()
            .seesListItems(40)

        activityScenario.close()
    }

    @Test
    fun onStart_withNegativeResponseFromServer_showsNoContentViewWithErrorView() {
        dispatcher.getApiResponse().billsApi.setServerErrorResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesNoDataView(appContext)
            .seesErrorMessage(serverErrorUtil.getErrorMessageByServerResponseCode(500))
            .doesNotSeeListItems()
            .doesNotSeeProgressIndicator()

        activityScenario.close()
    }

    @Test
    fun onStart_withOkButEmptyResponseFromServer_showsNoContentView() {
        dispatcher.getApiResponse().billsApi.setOkWithNoBillsResponse(appContext)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesNoDataView(appContext)
            .doesNotSeeListItems()
            .doesNotSeeProgressIndicator()

        activityScenario.close()
    }

    @Test
    fun onStart_withPositiveResponseAndClickOnItem_openDetailScreen() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesListViewComponentIsVisible()
            .seesListItems(40)
            .scrollToItem(1)

        Intents.init()
        robot.clickOnItem(1)
        Intents.intended(IntentMatchers.hasComponent(DetailsActivity::class.java.name))
        Intents.release()

        activityScenario.close()
    }

    // TODO add on no network test case

}
