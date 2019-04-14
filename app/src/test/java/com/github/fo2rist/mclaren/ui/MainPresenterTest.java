package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.repository.RaceCalendarRepository;
import com.github.fo2rist.mclaren.ui.models.RaceCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    private MainPresenter presenter;
    private MainScreenContract.View mockView;
    private EventsLogger mockEventsLogger;
    private RaceCalendarRepository mockCalendarRepository;

    @Before
    public void setUp() throws Exception {
        mockView = mock(MainScreenContract.View.class);
        mockEventsLogger = mock(EventsLogger.class);
        mockCalendarRepository = mock(RaceCalendarRepository.class);
        when(mockCalendarRepository.loadCalendar()).thenReturn(new RaceCalendar());
        presenter = new MainPresenter(mockView, mockEventsLogger, mockCalendarRepository);
    }

    @Test
    public void test_onStart_loadsStories() {
        presenter.onStart();

        verify(mockView).openStories();
    }

    @Test
    public void test_onStoriesClicked() {
        presenter.onStoriesClicked();

        verify(mockView).openStories();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onCircuitsClicked() {
        presenter.onCircuitsClicked();

        verify(mockView).openCircuits();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onDriversClicked(){
        presenter.onDriversClicked();

        verify(mockView).openDrivers();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onAboutClicked(){
        presenter.onAboutClicked();

        verify(mockView).openAboutScreen();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onCarClicked(){
        presenter.onCarClicked();

        verify(mockView).navigateTo(getMcLarenCarLink());
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onOfficialSiteClicked(){
        presenter.onOfficialSiteClicked();

        verify(mockView).navigateTo(getMcLarenFormula1Link());
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onTransmissionCenterClicked() {
        presenter.onTransmissionCenterClicked();

        verify(mockView).openTransmissionCenter();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }
}
