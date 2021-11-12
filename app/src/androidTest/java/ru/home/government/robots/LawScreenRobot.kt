package ru.home.government.robots

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import ru.home.government.R
import ru.home.government.matchers.CustomMatchers
import ru.home.government.screens.laws.main.LawsAdapter

class LawScreenRobot {

    fun doesNotSeeListItems(): LawScreenRobot {
        seesListItems(0)
        return this
    }

    fun seesListItems(count: Int): LawScreenRobot {
        onView(withId(R.id.list))
            .check(matches(CustomMatchers().recyclerViewSizeMatch(count)))
        return this
    }

    fun seesNavigationBar(): LawScreenRobot {
        onView(withId(R.id.nav_view))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesListViewComponentIsVisible(): LawScreenRobot {
        onView(withId(R.id.list))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesNoDataView(targetContext: Context): LawScreenRobot {
        onView(withId(R.id.lawsNoData))
            .check(matches(isDisplayed()))
            .check(matches(withText(targetContext.getString(R.string.laws_no_data_for_this_filter))))
        return this
    }

    fun scrollToItem(idx: Int): LawScreenRobot {
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<LawsAdapter.ViewHolder>(
                    idx,
                    ViewActions.scrollTo()
                )
            )
        return this
    }

    fun clickOnItem(idx: Int) {
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<LawsAdapter.ViewHolder>(
                    idx,
                    ViewActions.click()
                )
            )
    }
}