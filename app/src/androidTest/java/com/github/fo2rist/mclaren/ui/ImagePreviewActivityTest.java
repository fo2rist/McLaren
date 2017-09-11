package com.github.fo2rist.mclaren.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.PreviewPage;
import com.github.fo2rist.mclaren.utilities.ActivityTestBase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static com.github.fo2rist.mclaren.testdata.FeedItems.GALLERY_ITEM;
import static com.github.fo2rist.mclaren.utilities.CustomViewAssertions.displayed;

@RunWith(AndroidJUnit4.class)
public class ImagePreviewActivityTest extends ActivityTestBase {
    @Rule
    public ActivityTestRule<PreviewActivity> rule
            = new ActivityTestRule<>(PreviewActivity.class, false, false);
    private PreviewPage page = new PreviewPage();

    @Test
    public void testLoadsGallery() throws Exception {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, GALLERY_ITEM));

        page.onToolbarImage()
                .check(matches(withEffectiveVisibility(GONE)));
        page.onGalleyView()
                .check(displayed())
                .perform(swipeLeft());
    }
}
