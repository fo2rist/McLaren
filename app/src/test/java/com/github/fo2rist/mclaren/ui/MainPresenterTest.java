package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.calendar.CalendarEventsLoader;
import com.github.fo2rist.mclaren.ui.calendar.RaceCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    private MainPresenter presenter;
    private MainScreenContract.View mockView;
    private EventsLogger mockEventsLogger;
    private CalendarEventsLoader mockCalendarLoader;

    @Before
    public void setUp() throws Exception {
        mockView = mock(MainScreenContract.View.class);
        mockEventsLogger = mock(EventsLogger.class);
        mockCalendarLoader = mock(CalendarEventsLoader.class);
        when(mockCalendarLoader.loadCurrentCalendar()).thenReturn(new RaceCalendar());
        presenter = new MainPresenter(mockEventsLogger, mockCalendarLoader);
    }

    private void setUpPresenter() {
        presenter.onStart(mockView);
        reset(mockView);
        reset(mockEventsLogger);
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
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onCircuitsClicked() {
        setUpPresenter();

        presenter.onCircuitsClicked();

        verify(mockView).openCircuits();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onDriversClicked(){
        setUpPresenter();

        presenter.onDriversClicked();

        verify(mockView).openDrivers();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }
    @Test
    public void test_onNewsFeedClicked(){
        setUpPresenter();

        presenter.onNewsFeedClicked();

        verify(mockView).openNewsFeed();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onAboutClicked(){
        setUpPresenter();

        presenter.onAboutClicked();

        verify(mockView).openAboutScreen();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onCarClicked(){
        setUpPresenter();

        presenter.onCarClicked();

        verify(mockView).navigateTo(getMcLarenCarLink());
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onOfficialSiteClicked(){
        setUpPresenter();

        presenter.onOfficialSiteClicked();

        verify(mockView).navigateTo(getMcLarenFormula1Link());
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onTransmissionCenterClicked() {
        setUpPresenter();

        presenter.onTransmissionCenterClicked();

        verify(mockView).openTransmissionCenter();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }
}
