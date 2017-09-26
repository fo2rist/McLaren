package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.CircuitDetailsPage;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import java.util.Date;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.testutilities.CustomViewAssertions.displayed;

@RunWith(AndroidJUnit4.class)
public class CircuitDetailsPageTest {

    private static final String ID = "id";
    private static final String COUNTRY_CODE = "CODE";
    private static final String TRACK_NAME = "track name";
    private static final String CITY = "City";
    private static final String GP_NAME = "GP name";
    private static final Date START_DATE = new Date(1, 1, 1);

    private static final CalendarEvent EVENT
            = new CalendarEvent(ID, COUNTRY_CODE, TRACK_NAME, CITY, GP_NAME, START_DATE);

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
        launchActivity(EVENT);

        page.onCircuitTitle()
                .check(displayed())
                .check(matches(withText(GP_NAME)));

        page.onCircuitImage()
                .check(displayed());

        page.onCircuitDetails()
                .check(displayed())
                .check(matches(withText(CITY + " >> " + TRACK_NAME)));
    }

    private void launchActivity(CalendarEvent event) {
        rule.launchActivity(
                CircuitDetailsActivity.createIntent(context, event));
    }
}
