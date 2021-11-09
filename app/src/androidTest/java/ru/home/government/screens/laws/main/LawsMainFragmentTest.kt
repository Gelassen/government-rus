package ru.home.government.screens.laws.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.R
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.screens.MainActivity

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsMainFragmentTest {

//    @get:Rule
//    var detailsActivityIntentsRule = IntentsTestRule<DetailsActivity>(DetailsActivity::class.java)

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun onStart_openMainScreen_checkThereIsAnListItem() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<LawsAdapter.ViewHolder>(12, scrollTo()))
        onView(withId(R.id.list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<LawsAdapter.ViewHolder>(12, click()))

        // TODO check details activity has been launched
//        intended(hasComponent(DetailsActivity::class.java!!.getName()))
        // view check as a workaround to track launched activity
        onView(withId(R.id.viewpager))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        activityScenario.close()
    }
}