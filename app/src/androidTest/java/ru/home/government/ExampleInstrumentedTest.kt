package ru.home.government

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
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
import ru.home.government.di.modules.AppModule
import ru.home.government.di.DaggerTestApplicationComponent
import ru.home.government.di.FakeRepositoryModule
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.fakes.FakeBillPagingSource
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.matchers.CustomMatchers
import ru.home.government.screens.MainActivity
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

/*    inner class CustomFakeRepositoryModule(context: Context) : FakeRepositoryModule(context) {

        override fun providesBillsPagingSource(client: IApi): BillsPagingSource {
            return pagingSource
        }
    }

//    @Inject
//    lateinit var module: FakeRepositoryModule*/

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Inject
    lateinit var pagingSource: FakeBillPagingSource

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)

        // TODO implement custom test runner https://developer.android.com/codelabs/android-dagger#13
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        ((appContext as TestApplication)
            .getComponent() as TestApplicationComponent)
            .inject(this)
        pagingSource.setOkWithFullPayloadResponse()

/*        DaggerAppComponent
            .builder()
            .appModule(AppModule(appContext))
            .repositoryModule(RepositoryModule(appContext))
            .build()*/

//        component.inject(this)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

    @Test
    fun useAppContext() {
        pagingSource.setErrorResponse()

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
}
