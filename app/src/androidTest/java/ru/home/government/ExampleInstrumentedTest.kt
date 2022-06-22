package ru.home.government

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.matchers.CustomMatchers
import ru.home.government.repository.pagination.BillsPagingSource
import ru.home.government.screens.MainActivity
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@Ignore
class ExampleInstrumentedTest {

    // TODO fix me after disabling FakeBillsPagingSource and migrating to mock web server

    @Inject
    lateinit var pagingSource: BillsPagingSource

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        // implement custom test runner https://developer.android.com/codelabs/android-dagger#13
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        ((appContext as TestApplication)
            .getComponent() as TestApplicationComponent)
            .inject(this)


        // FIXME
        /*pagingSource.setOkWithFullPayloadResponse()*/
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun useAppContext() {
        // FIXME
        /*pagingSource.setErrorResponse()*/

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.home.government", appContext.packageName)


        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.list))
            .check(matches(CustomMatchers().recyclerViewSizeMatch(0)))

        activityScenario.close()
    }

    @Test
    fun onOpenAssets_withNonEmptyAssets_receivesAsset() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertNotNull(context)

        val result = context.assets.open("mocks/mock_api_deputies.json")
        assertNotNull(result)
    }
}
