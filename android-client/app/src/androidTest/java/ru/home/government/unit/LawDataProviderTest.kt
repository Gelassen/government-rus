package ru.home.government.unit

import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.home.government.providers.LawDataProvider

//@RunWith(AndroidJUnit4::class)
@SmallTest
class LawDataProviderTest {

    private lateinit var subject: LawDataProvider

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        subject  = LawDataProvider(context)
    }

    @Test
    fun getIntroducedDateFormat_withNonNullDate_returnsDate() {
        val date = "2022-07-18"

        val formattedDateMsg = subject.provideFormattedIntroducedDate(date)

        Assert.assertEquals("Внесён: 18 Jul 2022", formattedDateMsg)
    }

    @Test
    fun getIntroducedDateFormat_withNullDate_returnsDefaultMsg() {
        val date = null

        val formattedDateMsg = subject.provideFormattedIntroducedDate(date)

        Assert.assertEquals("Внесён: Нет данных", formattedDateMsg)
    }

    @Test
    fun getIntroducedDateFormat_withEmptyDate_returnsDefaultMsg() {
        val date = ""

        val formattedDateMsg = subject.provideFormattedIntroducedDate(date)

        Assert.assertEquals("Внесён: Нет данных", formattedDateMsg)
    }
}