package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    private MainPresenter presenter;
    private MainScreenContract.View mockView = mock(MainScreenContract.View.class);

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(mock(EventsLogger.class));
    }

    private void setUpPresenter() {
        presenter.onStart(mockView);
        reset(mockView);
    }

    @Test
    public void test_onStart_loadsStories() {
        presenter.onStart(mockView);

        verify(mockView).openStories();
    }

    @Test
    public void test_onStoriesClicked() {
        setUpPresenter();

        presenter.onStoriesClicked();

        verify(mockView).openStories();
    }

    @Test
    public void test_onCircuitsClicked() {
        setUpPresenter();

        presenter.onCircuitsClicked();

        verify(mockView).openCircuits();
    }

    @Test
    public void test_onDriversClicked(){
        setUpPresenter();

        presenter.onDriversClicked();

        verify(mockView).openDrivers();
    }
    @Test
    public void test_onNewsFeedClicked(){
        setUpPresenter();

        presenter.onNewsFeedClicked();

        verify(mockView).openNewsFeed();
    }

    @Test
    public void test_onAboutClicked(){
        setUpPresenter();

        presenter.onAboutClicked();

        verify(mockView).openAboutScreen();
    }

    @Test
    public void test_onCarClicked(){
        setUpPresenter();

        presenter.onCarClicked();

        verify(mockView).navigateTo(getMcLarenCarLink());
    }

    @Test
    public void test_onOfficialSiteClicked(){
        setUpPresenter();

        presenter.onOfficialSiteClicked();

        verify(mockView).navigateTo(getMcLarenFormula1Link());
    }

    @Test
    public void test_onTransmissionCenterClicked() {
        setUpPresenter();

        presenter.onTransmissionCenterClicked();

        verify(mockView).openTransmissionCenter();
    }
}
