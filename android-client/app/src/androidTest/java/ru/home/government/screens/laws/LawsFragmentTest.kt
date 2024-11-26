package ru.home.government.screens.laws

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import ru.home.government.BaseApiTest
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.robots.LawScreenRobot

@LargeTest
@Ignore("Test cases from this test suite has been moved to more specific test suite")
@RunWith(AndroidJUnit4::class)
class LawsFragmentTest : BaseApiTest() {

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    private val robot: LawScreenRobot = LawScreenRobot()

    @Before
    override fun setUp() {
        super.setUp()
        IdlingRegistry.getInstance().register(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        IdlingRegistry.getInstance().unregister(NetworkIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }

}