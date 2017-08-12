package com.github.fo2rist.mclaren;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.CircuitDetailsPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.utilities.CustomViewAssertions.displayed;

@RunWith(AndroidJUnit4.class)
public class CircuitDetailsPageTest {
    private static final String MONACO_GP_NAME = "Monaco GP";
    private static final int MONACO_GP_INDEX = 5;
    private static final String AUSTRALIA_GP_NAME = "Australia GP";
    private static final int AUSTRALIA_GP_INDEX = 0;
    @Rule
    public ActivityTestRule<CircuitDetailsActivity> rule
            = new ActivityTestRule<>(CircuitDetailsActivity.class, false, false);
    private CircuitDetailsPage page = new CircuitDetailsPage();
    private Context context;

    @Before
    public void setUp() throws Exception {
        this.context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testMonacoLayoutPresent() throws Exception {
        launchActivity(MONACO_GP_NAME, MONACO_GP_INDEX);

        assertCircuitDetailsPresent(MONACO_GP_NAME);
    }

    @Test
    public void testAustraliaLayoutPresent() throws Exception {
        launchActivity(AUSTRALIA_GP_NAME, AUSTRALIA_GP_INDEX);

        assertCircuitDetailsPresent(AUSTRALIA_GP_NAME);
    }

    private void assertCircuitDetailsPresent(String circuitName) {
        page.onCircuitTitle()
                .check(displayed())
                .check(matches(withText(circuitName)));
        page.onCircuitImage()
                .check(displayed());
    }

    private void launchActivity(String circuitName, int circuitNumber) {
        rule.launchActivity(
                CircuitDetailsActivity.createIntent(context, circuitName, circuitNumber));
    }
}
