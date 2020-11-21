package edu.uoc.pac4

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import edu.uoc.pac4.data.di.dataModule
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.ui.twitch.streams.StreamsActivity
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject


/**
 * Created by alex on 24/10/2020.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class Ex4Test : TwitchTest(), KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(InstrumentationRegistry.getInstrumentation().targetContext)
        modules(dataModule)
    }

    private val streamsRepository: StreamsRepository by inject()

    @Test
    fun retrievesStreams() {
        runBlocking {
            val response = streamsRepository.getStreams()
            assert(!response.second.isNullOrEmpty()) {
                "Streams response cannot be empty"
            }
        }
    }

}