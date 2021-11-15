package ru.home.government.screens.laws.details

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.TestApplication
import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.FakeRepositoryModule
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.fakes.FakeRepository
import ru.home.government.di.modules.AppModule
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider
import ru.home.government.repository.GovernmentRepository
import ru.home.government.robots.LawDetailsRobot
import ru.home.government.stubs.Stubs.ApiResponse.getPositiveButIncompleteServerResponse
import ru.home.government.stubs.Stubs.ApiResponse.getPositiveServerResponse
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawDetailsFragmentTest {

    @Inject
    lateinit var repository: GovernmentRepository

    lateinit var dataProvider: LawDataProvider

    private val robot: LawDetailsRobot = LawDetailsRobot()

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        dataProvider = LawDataProvider(withContext())

        val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestApplication
        application.component = DaggerTestApplicationComponent
            .builder()
            .appModule(AppModule(application))
            .fakeRepositoryModule(FakeRepositoryModule(application))
            .build()

        (application
            .getComponent() as TestApplicationComponent)
            .inject(this)

        (repository as FakeRepository).setPositiveLawByNumberResponse()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_withPositiveScenarioAndFullData_seeData() {
        val input = getPositiveServerResponse().laws.get(0)!!
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .seesTitle(input.name)
            .seesIntroducedDate(dataProvider.provideFormattedIntroducedDate(input.introductionDate))
            .seesUpdateDate(dataProvider.provideLastEventDate(input.lastEvent))
            .seesResolution(dataProvider.provideFormattedResolution(input.lastEvent.solution as String?))
            .doesNotSeeDeputies()
            .seesResponsible(dataProvider.provideResponsibleCommittee(input.committees))
            .seesLastEvent(dataProvider.provideLastEventData(input.lastEvent))

        activityScenario.close()
    }

    @Test
    fun onStart_withPositiveScenarioAndIncompleteData_seesData() {
        (repository as FakeRepository).setPositiveButIncompleteLawByNumberResponse()
        val input = getPositiveButIncompleteServerResponse().laws.get(0)
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .seesTitle(input.name)
            .seesIntroducedDate(dataProvider.provideFormattedIntroducedDate(input.introductionDate))
            .seesUpdateDate(dataProvider.provideLastEventDate(input.lastEvent))
            .seesResolution(dataProvider.provideFormattedResolution(input.lastEvent.solution as String?))
            .seesDeputies()
            .seesResponsible(dataProvider.provideResponsibleCommittee(input.committees))
            .seesLastEvent(dataProvider.provideLastEventData(input.lastEvent))

        activityScenario.close()
    }

    private fun withContext(): Context {
        return InstrumentationRegistry.getInstrumentation().targetContext!!
    }

    private fun startActivityScenario(law: Law): ActivityScenario<DetailsActivity>? {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_LAW_CODE, law.number)
        intent.putExtra(DetailsActivity.EXTRA_DETAILS_URL, law.url)
        intent.putExtra(DetailsActivity.EXTRA_DISCUSSIONS_URL, law.transcriptUrl)

        return ActivityScenario.launch<DetailsActivity>(intent)
    }

}