package ru.home.government.matchers

import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedDiagnosingMatcher
import androidx.test.espresso.matcher.BoundedMatcher
import com.skydoves.androidveil.VeilRecyclerFrameView
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

class CustomMatchers() {

    fun recyclerViewSizeMatch(matcherSize: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("RecyclerView with list size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }

    fun isShimmerVeiled(): BoundedDiagnosingMatcher<View, VeilRecyclerFrameView> {
        return object : BoundedDiagnosingMatcher<View, VeilRecyclerFrameView>(VeilRecyclerFrameView::class.java) {
            override fun matchesSafely(
                item: VeilRecyclerFrameView,
                mismatchDescription: Description
            ): Boolean {
                return item.isVeiled
            }

            override fun describeMoreTo(description: Description) {
                description.appendText("Shimmer should be displayed")
            }

        }
    }
    @Deprecated("not completed and redundant")
    fun recyclerViewInsideShimmerComponent(matcherSize: Int): Matcher<View> {
        return object : BaseMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Shimmer component with two RecyclerViews inside")
            }

            override fun matches(actual: Any?): Boolean {
                if ((actual as ViewGroup).childCount == 2
                    && actual.get(0) is RecyclerView) {
                    return (actual.get(0) as RecyclerView).adapter!!.itemCount == matcherSize
                } else {
                    return false
                }
            }

        }
    }
}