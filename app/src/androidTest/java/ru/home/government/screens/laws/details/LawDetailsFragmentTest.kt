package ru.home.government.screens.laws.details

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.BaseApiTest
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.model.Committees
import ru.home.government.model.LastEvent
import ru.home.government.model.Law
import ru.home.government.providers.LawDataProvider
import ru.home.government.repository.GovernmentRepository
import ru.home.government.robots.LawDetailsRobot
import javax.inject.Inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawDetailsFragmentTest : BaseApiTest() {

    lateinit var dataProvider: LawDataProvider

    private val robot: LawDetailsRobot = LawDetailsRobot()

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    object Const {
        const val idxBillWithoutDeputies = 0
        const val idxBillWithDeputies = 3
    }

    @Before
    override fun setUp() {
        super.setUp()
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        dataProvider = LawDataProvider(appContext)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_withServerErrorResponse_seesPlaceholders() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8ServerErrorResponse()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillEmptyVotesResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .seesTitle("")
            .seesIntroducedDate(dataProvider.provideFormattedIntroducedDate(""))
            .seesUpdateDate(dataProvider.provideLastEventDate(LastEvent()))
            .seesResolution(dataProvider.provideFormattedResolution(""))
            .doesNotSeeDeputies()
            .seesResponsible(dataProvider.provideResponsibleCommittee(Committees()))
            .seesLastEvent(dataProvider.provideLastEventData(LastEvent()))
            .seesErrorMessage(appContext)

        activityScenario.close()
    }

    @Test
    fun onStart_withVotesServerErrorResponse_seesPlaceholderWithErrorView() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8Response()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillVotesServerErrorResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
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
            .openLawVotesPages()
            .seesVotesPlaceholder()
            .seesErrorMessage(appContext)

        activityScenario.close()
    }

    @Test
    fun onStart_withPositiveScenarioAndFullData_seesData() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8Response()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillEmptyVotesResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
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
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBillWithDeputiesResponse()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillEmptyVotesResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithDeputies)
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

    @Test
    fun onStart_withPositiveResponseAndOpenDeputiesScreen_seesDeputies() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBillWithDeputiesResponse()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillEmptyVotesResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithDeputies)
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .seesTitle(input.name)
            .seesDeputies()

        Intents.init()
        robot.clickOnDeputiesCounter()
        Intents.intended(IntentMatchers.hasComponent(DeputiesOnLawActivity::class.java.name))
        Intents.release()

        activityScenario.close()
    }

    @Test
    fun onOpenDetailsPage_withPositiveResponse_seesOverviewPage() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8Response()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillEmptyVotesResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .seesLawPageTitle()
            .seesLawDetailsPageTitle()
            .openLawDetailsPage()

        activityScenario.close()
    }

    @Test
    fun onOpenVotesPage_withPositiveResponseAndOpenVotesPage_seesVotesPage() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8Response()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillVotesFullResponse()
        val votesFull = dispatcher.getApiResponse().billSpecificVotesApi.getVotesFull()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .openLawVotesPages()
            .seesVotesCard(votesFull)

        activityScenario.close()
    }

    @Test
    fun onOpenVotesPage_withPositiveButNoVotesResponse_seesVotesPageWithPlaceholder() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8Response()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillEmptyVotesResponse()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
        val activityScenario = startActivityScenario(input)
        dataBindingIdlingResource.monitorActivity(activityScenario!!)

        robot
            .withContext(ApplicationProvider.getApplicationContext())
            .seesLawVotesPageTitle()
            .openLawVotesPages()
            .seesVotesPlaceholder()

        activityScenario.close()
    }

    // test response on two different law ids
    @Test
    fun onOpenVotesPage_withPositiveResponseAndCheckTwoDifferentLaws_seesTwoDistinguishedLaws() {
        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBill149922_8Response()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillVotesFullResponse()
        val votesFull = dispatcher.getApiResponse().billSpecificVotesApi.getVotesFull()
        val input = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithoutDeputies)
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
                .openLawVotesPages()
                .seesVotesCard(votesFull)

        activityScenario.close()

        dispatcher.getApiResponse().billsApi.setOkBillsResponse(appContext)
        dispatcher.getApiResponse().billsApi.set2ndPageOkBillsResponse(appContext)
        dispatcher.getApiResponse().billSpecificApi.setBillWithDeputiesResponse()
        dispatcher.getApiResponse().billSpecificVotesApi.setBillVotesFull2ndResponse()
        val anotherVotesFull = dispatcher.getApiResponse().billSpecificVotesApi.getVotesFull2nd()
        val anotherInput = dispatcher.getApiResponse().billsApi.getBillsCollection(appContext).laws.get(Const.idxBillWithDeputies)
        val anotherActivityScenario = startActivityScenario(anotherInput)
        dataBindingIdlingResource.monitorActivity(anotherActivityScenario!!)

        robot
                .withContext(ApplicationProvider.getApplicationContext())
                .seesTitle(anotherInput.name)
                .seesIntroducedDate(dataProvider.provideFormattedIntroducedDate(anotherInput.introductionDate))
                .seesUpdateDate(dataProvider.provideLastEventDate(anotherInput.lastEvent))
                .seesResolution(dataProvider.provideFormattedResolution(anotherInput.lastEvent.solution as String?))
                .seesDeputies()
                .seesResponsible(dataProvider.provideResponsibleCommittee(anotherInput.committees))
                .seesLastEvent(dataProvider.provideLastEventData(anotherInput.lastEvent))
                .openLawVotesPages()
                .seesVotesCard(anotherVotesFull)

        anotherActivityScenario.close()

        assertNotEquals(anotherInput.id, input.id)
        assertNotEquals(anotherVotesFull.forCount, votesFull.forCount)
    }

    // TODO test case with empty specific bill response
    // TODO test case with web url loaded
    // test with load webpage as idle resource

    private fun startActivityScenario(law: Law): ActivityScenario<DetailsActivity>? {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_LAW_CODE, law.number)
        intent.putExtra(DetailsActivity.EXTRA_DETAILS_URL, law.url)
        intent.putExtra(DetailsActivity.EXTRA_DISCUSSIONS_URL, law.transcriptUrl)

        return ActivityScenario.launch<DetailsActivity>(intent)
    }

}