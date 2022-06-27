package ru.home.government.screens.laws.filter

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

import ru.home.government.BaseApiTest
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.robots.LawScreenRobot

@LargeTest
@RunWith(AndroidJUnit4::class)
class LawsFilteredFragmentTest : BaseApiTest() {

    private val robot: LawScreenRobot = LawScreenRobot()

    private val dataBindingIdlingResource = DataBindingIdlingResource()

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