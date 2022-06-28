package ru.home.government.screens.laws

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import ru.home.government.BaseApiTest
import ru.home.government.R
import ru.home.government.TestApplication
import ru.home.government.di.TestApplicationComponent
import ru.home.government.di.test.NetworkIdlingResource
import ru.home.government.idlingresource.DataBindingIdlingResource
import ru.home.government.idlingresource.monitorActivity
import ru.home.government.network.ServerErrorUtil
import ru.home.government.repository.GovernmentRepository
import ru.home.government.robots.LawScreenRobot
import ru.home.government.screens.MainActivity
import ru.home.government.screens.laws.details.DetailsActivity
import javax.inject.Inject

@LargeTest
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