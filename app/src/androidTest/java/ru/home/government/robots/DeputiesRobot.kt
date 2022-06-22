package ru.home.government.robots

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import ru.home.government.R
import java.util.regex.Matcher

class DeputiesRobot : BaseRobot() {

    override fun seesListItems(count: Int): DeputiesRobot {
        return super.seesListItems(count) as DeputiesRobot
    }

    override fun seesErrorMessage(context: Context): DeputiesRobot {
        return super.seesErrorMessage(context) as DeputiesRobot
    }

    override fun doesNotSeeProgressIndicator(): DeputiesRobot {
        return super.doesNotSeeProgressIndicator() as DeputiesRobot
    }

    fun seesNavView(): DeputiesRobot {
        onView(withId(R.id.nav_view))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesNoContent(): DeputiesRobot {
        onView(withId(R.id.deputiesNoData))
            .check(matches(isDisplayed()))
        return this
    }

    fun doesNotSeeNoContent(): DeputiesRobot {
        onView(withId(R.id.deputiesNoData))
            .check(matches(not(isDisplayed())))
        return this
    }

    /* actions */
    fun clickDeputiesTab() {
        onView(withId(R.id.navigation_home))
            .perform(ViewActions.click())
    }

}