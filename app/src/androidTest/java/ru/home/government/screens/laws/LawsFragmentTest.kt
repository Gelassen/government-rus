package ru.home.government.screens.laws

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.R
import ru.home.government.TestApplication
import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.FakeRepositoryModule
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.fakes.FakeBillPagingSource
import ru.home.government.di.modules.AppModule
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.repository.GovernmentRepository
import ru.home.government.robots.LawScreenRobot
import ru.home.government.screens.MainActivity
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsFragmentTest {

    @Inject
    lateinit var pagingSource: FakeBillPagingSource

    @Inject
    lateinit var repository: GovernmentRepository

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val robot: LawScreenRobot = LawScreenRobot()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        application.component = DaggerTestApplicationComponent
            .builder()
            .appModule(AppModule(application))
            .fakeRepositoryModule(FakeRepositoryModule(application))
            .build()

        (application
            .getComponent() as TestApplicationComponent)
            .inject(this)
        pagingSource.setOkWithFullPayloadResponse()
    }

    @After
    fun tearDown() {
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
    @Ignore
    fun onStart_withEnterSearchWordForPositiveCase_showsTwoItems() {
        pagingSource.setOkWithEmptyPayload()
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        pagingSource.setOkWithFullPayloadResponse()

        robot.clickSearchItem()
        robot.enterSearchQuery("medicine")
        robot.seesListItems(R.id.list_laws_filtered,2)

        activityScenario.close()
    }
}