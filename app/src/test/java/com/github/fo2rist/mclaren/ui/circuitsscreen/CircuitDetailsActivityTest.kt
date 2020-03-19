package com.github.fo2rist.mclaren.ui.circuitsscreen

import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.testdata.CalendarEvents.createDummyEvent
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CircuitDetailsActivityTest {

    private val context by lazy { RuntimeEnvironment.application.applicationContext }

    @Test
    fun `test starts with circuit details fragment`() {
        val circuitDetailsActivity = Robolectric.buildActivity(
                CircuitDetailsActivity::class.java, CircuitDetailsActivity.createIntent(context, createDummyEvent())
        ).setup().get()

        assertNotNull(circuitDetailsActivity.findViewById(R.id.circuit_details_root))
    }
}
