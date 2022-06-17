package ru.home.government.robots

import android.content.Context
import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
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
        seesListItems(resId = R.id.list, count)
        return this
    }

    fun seesListItems(resId: Int, count: Int): LawScreenRobot {
        onView(withId(resId))
            .check(matches(CustomMatchers().recyclerViewSizeMatch(count)))
        return this
    }

    fun seesNavigationBar(): LawScreenRobot {
        onView(withId(R.id.nav_view))
            .check(matches(isDisplayed()))
        return this
    }

    fun seesListViewComponentIsVisible(): LawScreenRobot {
        seesListViewComponentIsVisible(R.id.list)
        return this
    }

    fun seesListViewComponentIsVisible(resId: Int): LawScreenRobot {
        onView(withId(resId))
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

    fun clickSearchItem() {
        onView(withId(R.id.action_search))
            .perform(click())
    }

    fun enterSearchQuery(query: String) {
        onView(withId(R.id.searchView))
            .check(matches(isDisplayed()))
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText(query), pressKey(KeyEvent.KEYCODE_ENTER))
    }
}