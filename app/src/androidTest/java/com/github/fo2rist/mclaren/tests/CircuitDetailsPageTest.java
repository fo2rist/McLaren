package com.github.fo2rist.mclaren.tests;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.CircuitDetailsPage;
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import java.util.Date;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.utils.CustomViewAssertions.displayed;
import static org.hamcrest.Matchers.any;

@RunWith(AndroidJUnit4.class)
public class CircuitDetailsPageTest {

    private static final String ID = "monaco";
    private static final String COUNTRY_CODE = "CODE";
    private static final String TRACK_NAME = "track name";
    private static final String CITY = "City";
    private static final String GP_NAME = "GP name";
    private static final int LAPS = 11;
    private static final double LENGTH = 12.34;
    private static final double DISTANCE = 56.789;
    private static final String SEASONS = "2001";
    private static final int GP_HELD = 1111;
    private static final String WIKI_LINK = "http://wiki.link";
    private static final Date START_DATE = new Date(1, 1, 1);

    private static final CalendarEvent EVENT = new CalendarEvent(ID,
            COUNTRY_CODE, TRACK_NAME, CITY, GP_NAME, LAPS, LENGTH, DISTANCE, SEASONS, GP_HELD, WIKI_LINK, START_DATE);

    @Rule
    public IntentsTestRule<CircuitDetailsActivity> rule
            = new IntentsTestRule<>(CircuitDetailsActivity.class, false, false);
    private CircuitDetailsPage page = new CircuitDetailsPage();
    private Context context;

    @Before
    public void setUp() throws Exception {
        this.context = InstrumentationRegistry.getTargetContext();
        launchActivity(EVENT);
    }

    @Test
    public void testMonacoLayoutPresent() throws Exception {
        page.onCircuitTitle()
                .check(displayed())
                .check(matches(withText(GP_NAME)));

        page.onCircuitImage()
                .check(displayed());

        page.onCircuitDetails()
                .check(displayed())
                .check(matches(withText(CITY + " >> " + TRACK_NAME)));

        page.onDetailItem("Laps")
                .perform(scrollTo())
                .check(matches(withText(LAPS + "")));

        page.onDetailItem("Length")
                .perform(scrollTo())
                .check(matches(withText(String.format("%.3f km", LENGTH))));

        page.onDetailItem("Total Distance")
                .perform(scrollTo())
                .check(matches(withText(String.format("%.3f km", DISTANCE))));

        page.onDetailItem("Seasons")
                .perform(scrollTo())
                .check(matches(withText(SEASONS)));

        page.onDetailItem("Grand Prix Held")
                .perform(scrollTo())
                .check(matches(withText(GP_HELD + "")));
    }

    @Test
    public void testTitleInfoLeadToWebsite() throws Exception {
        page.onCircuitTitle()
                .perform(click());

        intended(hasData(any(Uri.class)));
    }

    private void launchActivity(CalendarEvent event) {
        rule.launchActivity(
                CircuitDetailsActivity.createIntent(context, event));
    }
}
