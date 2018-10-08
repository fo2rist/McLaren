package com.github.fo2rist.mclaren.tests;

import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.PreviewPage;
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity;
import com.github.fo2rist.mclaren.utils.ActivityTestBase;
import com.github.fo2rist.mclaren.utils.WebActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.GONE;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webContent;
import static android.support.test.espresso.web.matcher.DomMatchers.hasElementWithXpath;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.github.fo2rist.mclaren.testdata.FeedItems.HTML_ARTICLE_ITEM;
import static com.github.fo2rist.mclaren.utils.CustomViewAssertions.displayed;

@RunWith(AndroidJUnit4.class)
public class WebPreviewActivityTest extends ActivityTestBase {
    @Rule
    public WebActivityTestRule<PreviewActivity> rule
            = new WebActivityTestRule<>(PreviewActivity.class, false, false);
    private PreviewPage page = new PreviewPage();

    @Test
    public void testLoadsArticle() throws Exception {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, HTML_ARTICLE_ITEM));

        onWebView()
                .check(webContent(hasElementWithXpath("/html/body/div/p/strong")))
                .check(webContent(hasElementWithXpath("/html/body/div/h2/strong")))
                .check(webContent(hasElementWithXpath("/html/body/div/table/tbody/tr/td")));
    }

    @Test
    public void testLayout() throws Exception {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, HTML_ARTICLE_ITEM));

        page.onToolbarImage()
                .check(matches(withEffectiveVisibility(VISIBLE)));
        page.onWebView()
                .check(displayed());
        page.onGalleyView()
                .check(doesNotExist());
    }

    @Test
    public void testToolbarImageInvisibleInUrlMode() throws Exception {
        rule.launchActivity(PreviewActivity.createUrlIntent(context, "http://address_that_doesnt_exist"));

        page.onToolbarImage()
                .check(matches(withEffectiveVisibility(GONE)));
    }
}
