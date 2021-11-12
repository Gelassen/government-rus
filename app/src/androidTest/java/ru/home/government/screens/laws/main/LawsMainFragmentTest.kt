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
import ru.home.government.TestApplication
import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.FakeRepositoryModule
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.fakes.FakeBillPagingSource
import ru.home.government.di.modules.AppModule
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.robots.LawScreenRobot
import ru.home.government.screens.MainActivity
import ru.home.government.screens.laws.details.DetailsActivity
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsMainFragmentTest {

    @Inject
    lateinit var pagingSource: FakeBillPagingSource

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val robot = LawScreenRobot()

    private lateinit var autoCloseable: AutoCloseable


    @Before
    fun setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this)

        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        // implement custom test runner https://developer.android.com/codelabs/android-dagger#13
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        val application = appContext as TestApplication
        application.component = DaggerTestApplicationComponent
            .builder()
            .appModule(AppModule(appContext))
            .fakeRepositoryModule(FakeRepositoryModule(appContext))
            .build()

        (application
            .getComponent() as TestApplicationComponent)
            .inject(this)

        pagingSource.setOkWithFullPayloadResponse()
    }

    @After
    fun tearDown() {
        autoCloseable.close()
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_withPositiveResponseFromServer_showsAllData() {
        pagingSource.setOkWithFullPayloadResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesListViewComponentIsVisible()
            .seesListItems(2)

        activityScenario.close()
    }

    @Test
    fun onStart_withPositiveResponseAndEmptyPayload_showsEmptyScreen() {
        pagingSource.setOkWithEmptyPayload()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesListViewComponentIsVisible()
            .doesNotSeeListItems()
            .seesNoDataView(InstrumentationRegistry.getInstrumentation().targetContext)

        activityScenario.close()
    }

    @Test
    fun onStart_withNegativeResponseFromServer_showsError() {
        pagingSource.setErrorResponse()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesListViewComponentIsVisible()
            .doesNotSeeListItems()

        activityScenario.close()
    }

    @Test
    fun onStart_withPositiveResponseFromServerAndTurnOffNetwork_showsError() {
        // no test
    }

    @Test
    fun onStart_whenServerIsUnavailable_showsError() {
        // no test
    }

    @Test
    fun onStart_withPositiveResponseAndClickOnItem_openDetailScreen() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        robot
            .seesNavigationBar()
            .seesListViewComponentIsVisible()
            .scrollToItem(1)

        Intents.init()
        robot.clickOnItem(1)
        Intents.intended(IntentMatchers.hasComponent(DetailsActivity::class.java.name))
        Intents.release()

        activityScenario.close()
    }

}
