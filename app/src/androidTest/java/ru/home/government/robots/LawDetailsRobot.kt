package ru.home.government.robots

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.core.IsNot.not
import ru.home.government.R
import ru.home.government.model.Vote

class LawDetailsRobot {

    private lateinit var context: Context

    fun withContext(context: Context): LawDetailsRobot {
        this.context = context
        return this
    }

    fun seesTitle(name: String): LawDetailsRobot {
        onView(withId(R.id.lawTitle))
            .check(matches(isDisplayed()))
            .check(matches(withText(name)))
        return this
    }

    fun seesIntroducedDate(introductionDate: String): LawDetailsRobot {
        onView(withId(R.id.lawIntroducedDate))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring(context.getString(R.string.title_introduced))))
            .check(matches(withSubstring(introductionDate)))
        return this
    }

    fun seesUpdateDate(lastEventDate: String): LawDetailsRobot {
        onView(withId(R.id.lawUpdateDate))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring(context.getString(R.string.title_update))))
            .check(matches(withSubstring(lastEventDate)))
        return this
    }

    fun seesResolution(solution: String): LawDetailsRobot {
        onView(withId(R.id.lawResolution))
            .check(matches(isDisplayed()))
            .check(matches(withSubstring(context.getString(R.string.title_resolution))))
            .check(matches(withSubstring(solution)))
        return this
    }

    fun seesDeputies(): LawDetailsRobot {
        onView(withId(R.id.voteDeputiesNoData))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.voteDeputies))
            .check(matches(isDisplayed()))
        onView(withId(R.id.voteDeputiesCounter))
            .check(matches(isDisplayed()))
        // TODO complete me
        return this
    }

    fun doesNotSeeDeputies(): LawDetailsRobot {
        onView(withId(R.id.voteDeputiesNoData))
            .check(matches(isDisplayed()))
        onView(withId(R.id.voteDeputiesCounter))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.voteDeputies))
            .check(matches(not(isDisplayed())))
        return this
    }

    fun seesResponsible(text: String): LawDetailsRobot {
        onView(withId(R.id.lawResponsible))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.title_responsible)))

        onView(withId(R.id.lawResponsibleCommittee))
            .check(matches(isDisplayed()))
            .check(matches(withText(text)))
        return this
    }

    fun seesLastEvent(lastEvent: String): LawDetailsRobot {
        onView(withId(R.id.lawLastEvent))
            .check(matches(isDisplayed()))
            .check(matches(withText(context.getString(R.string.title_last_event))))

        onView(withId(R.id.lawLastEventData))
            .check(matches(isDisplayed()))
            .check(matches(withText(lastEvent)))
        return this
    }

    fun clickOnDeputiesCounter(): LawDetailsRobot {
        onView(withId(R.id.voteDeputiesCounter))
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())
        return this
    }

    fun seesLawPageTitle(): LawDetailsRobot {
        onView(withText(R.string.title_law_tab_law))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesLawDetailsPageTitle() : LawDetailsRobot {
        onView(withText(R.string.title_law_tab_details))
            .check(matches(isDisplayed()))
        return this
    }

    fun openLawDetailsPage() : LawDetailsRobot {
        onView(withText(R.string.title_law_tab_details))
            .perform(ViewActions.click())

        onView(withId(R.id.webView))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesLawVotesPageTitle() : LawDetailsRobot {
        onView(withText(R.string.title_law_tab_votes))
            .check(matches(isDisplayed()))
        return this
    }

    fun openLawVotesPages(): LawDetailsRobot {
        onView(withText(R.string.title_law_tab_votes))
            .perform(ViewActions.click())
        return this
    }

    fun seesVotesPlaceholder(): LawDetailsRobot {
        onView(withId(R.id.votesNoData))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesVotesCard(data: Vote): LawDetailsRobot {
        onView(withId(R.id.votesContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.voteFor))
            .check(matches(withSubstring(data.forCount.toString())))

        onView(withId(R.id.voteAbstain))
            .check(matches(withSubstring(data.abstainCount.toString())))

        onView(withId(R.id.voteAgainst))
            .check(matches(withSubstring(data.againstCount.toString())))

        onView(withId(R.id.voteDetails))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.votes_details)))
        return this
    }
}