package ru.home.government.matchers

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import ru.home.government.App

class CustomMatchers {

    fun recyclerViewSizeMatch(matcherSize: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
//                Log.d(App.TEST, "Within custom matcher")
//                Log.d(App.TEST, "Instance of: " + recyclerView)
//                Log.d(App.TEST, "recyclerView.adapter!!.itemCount: " + recyclerView.adapter!!.itemCount)
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }
}