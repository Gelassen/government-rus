package ru.home.government.robots

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import ru.home.government.R
import ru.home.government.matchers.CustomMatchers

abstract class BaseRobot {

    open fun doesNotSeeListItems(): BaseRobot {
        seesListItems(resId = R.id.list, count = 0)
        return this
    }

    open fun doesNotSeeListItems(resId: Int): BaseRobot {
        seesListItems(resId = resId, count = 0)
        return this
    }

    open fun seesListItems(count: Int): BaseRobot {
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .check(ViewAssertions.matches(CustomMatchers().recyclerViewSizeMatch(count)))
        seesListItems(resId = R.id.list, count)
        return this
    }

    open fun seesListItems(resId: Int, count: Int): BaseRobot {
        Espresso.onView(ViewMatchers.withId(resId))
            .check(ViewAssertions.matches(CustomMatchers().recyclerViewSizeMatch(count)))
        return this
    }

    open fun seesErrorMessage(context: Context): BaseRobot {
        Espresso.onView(ViewMatchers.withId(com.google.android.material.R.id.snackbar_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        Matchers.containsString(
                            context.getString(
                                R.string.unknown_error
                            )
                        )
                    )
                )
            )
        return this
    }

    open fun doesNotSeeProgressIndicator(): BaseRobot {
        Espresso.onView(ViewMatchers.withId(R.id.progressView))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        return this
    }
}