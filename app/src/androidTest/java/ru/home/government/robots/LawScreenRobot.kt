package ru.home.government.robots

import android.content.Context
import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.StringContains.containsString
import ru.home.government.R
import ru.home.government.robots.Utils.atPositionByTitle
import ru.home.government.screens.laws.main.LawsAdapter

class LawScreenRobot : BaseRobot(){

    override fun doesNotSeeListItems(): LawScreenRobot {
        return super.doesNotSeeListItems() as LawScreenRobot
    }

    override fun doesNotSeeListItems(resId: Int): LawScreenRobot {
        return super.doesNotSeeListItems(resId) as LawScreenRobot
    }

    override fun doesNotSeeProgressIndicator(): LawScreenRobot {
        return super.doesNotSeeProgressIndicator() as LawScreenRobot
    }

    override fun seesErrorMessage(context: Context): LawScreenRobot {
        return super.seesErrorMessage(context) as LawScreenRobot
    }

    override fun seesListItems(count: Int, isShimmer: Boolean): LawScreenRobot {
        return super.seesListItems(count, isShimmer = isShimmer) as LawScreenRobot
    }

    override fun seesListItems(resId: Int, count: Int): LawScreenRobot {
        return super.seesListItems(resId, count) as LawScreenRobot
    }

    fun doesNotSeeProgressIndicator(resId: Int): LawScreenRobot {
        onView(allOf(withId(resId)))
            .check(matches(not(isDisplayed())))
        return this
    }

    fun seesErrorMessage(string: String): LawScreenRobot {
        onView(withId(R.id.snackbar_text))
            .check(matches(isDisplayed()))
            .check(matches(withText(Matchers.containsString(string))))
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
        onView(allOf(withId(R.id.lawsNoData), isDisplayed()))
//            .check(matches(isDisplayed()))
            .check(matches(withText(targetContext.getString(R.string.laws_no_data_for_this_filter))))
        return this
    }

    fun scrollToItem(idx: Int): LawScreenRobot {
        onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<LawsAdapter.ViewHolder>(
                    idx,
                    scrollTo()
                )
            )
        return this
    }

    fun seesListItemWithText(order: Int, text: String): LawScreenRobot {
        onView(withId(R.id.list_laws_filtered))
            .check(matches(isDisplayed()))
        onView(withId(R.id.list_laws_filtered))
            .check(matches(atPositionByTitle(order, withText(containsString(text)))))
        return this
    }

    fun doesNotSeeExpandSearchView(text: String): LawScreenRobot {
        /* did not find a direct way to check expanded mode of search view, so check it by 2nd criteria */
        onView(isAssignableFrom(EditText::class.java))
            .check(matches(not(isDisplayed())))
            .check(matches(not(withText(text))))
        return this
    }

    /* actions */

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
            .perform(replaceText(query))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
            /*.perform(typeText(query), pressKey(KeyEvent.KEYCODE_ENTER)) // Unicode chars is not supported here */
    }

}