package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.mvp.MainScreenContract;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import com.github.fo2rist.mclaren.repository.remoteconfig.RaceCalendarRepository;
import com.github.fo2rist.mclaren.ui.models.RaceCalendar;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.fo2rist.mclaren.testdata.CalendarEvents.createDummyEvent;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenCarLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getMcLarenFormula1Link;
import static com.nhaarman.mockitokotlin2.VerificationKt.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class MainPresenterTest {
    private static CalendarEvent activeEvent = createDummyEvent(new DateTime());
    private static CalendarEvent nextEvent = createDummyEvent(new DateTime().plusHours(1));
    private static int activeEventNumber = 1;
    private static int nextEventNumber = 2;

    private MainPresenter presenter;

    private MainScreenContract.View mockView;
    private EventsLogger mockEventsLogger;
    private RaceCalendar mockCalendar;

    @Before
    public void setUp() {
        mockView = mock(MainScreenContract.View.class);
        mockEventsLogger = mock(EventsLogger.class);
        mockCalendar = mock(RaceCalendar.class);
        RaceCalendarRepository mockCalendarRepository = mock(RaceCalendarRepository.class);
        when(mockCalendarRepository.loadCalendar()).thenReturn(mockCalendar);
        when(mockCalendar.indexOf(activeEvent)).thenReturn(activeEventNumber);
        when(mockCalendar.indexOf(nextEvent)).thenReturn(nextEventNumber);

        presenter = new MainPresenter(mockView, mockEventsLogger, mockCalendarRepository);
    }

    @Test
    public void test_onStart_loadsStories() {
        presenter.onStart();

        verify(mockView).openStories();
        verify(mockView, never()).showUpcomingEventButton(any(), any());
        verify(mockView, never()).showTransmissionButton();
    }

    @Test
    public void test_onStart_showsCountdown_whenNextEventUpcoming() {
        when(mockCalendar.getNextEvent()).thenReturn(createDummyEvent(new DateTime().plusHours(1)));

        presenter.onStart();

        verify(mockView).showUpcomingEventButton(any(), any());
    }

    @Test
    public void test_onStoriesClicked() {
        presenter.onStoriesClicked();

        verify(mockView).openStories();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onTeamTwitterClicked() {
        presenter.onTeeamTwitterClicked();

        verify(mockView).openTweets();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onCircuitsClicked() {
        presenter.onCircuitsClicked();

        verify(mockView).openCircuits();
        verify(mockView).hideFloatingButtons();
        verify(mockEventsLogger).logViewEvent(any(Events.class));
    }

    @Test
    public void test_onDriversClicked(){
        presenter.onDriversClicked();

        verify(mockView).openDrivers();
        verify(mockView).hideFloatingButtons();
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
        verify(mockEventsLogger).logViewEvent(Events.TRANSMISSION_CENTER);
    }

    @Test
    public void test_onUpcomingEventClicked_doesNothing_whenThereIsNoEvent() {
        presenter.onUpcomingEventClicked();

        verify(mockView, never()).openCircuitScreen(anyInt());
    }

    @Test
    public void test_onUpcomingEventClicked_openNextEvent_ifThereIsNoActiveEvent() {
        when(mockCalendar.getNextEvent()).thenReturn(nextEvent);

        presenter.onUpcomingEventClicked();

        verify(mockView).openCircuitScreen(nextEventNumber);
        verify(mockEventsLogger).logViewEvent(Events.DETAILS_CIRCUIT);
    }

    @Test
    public void test_onUpcomingEventClicked_opensActiveEvent_ifItsActive() {
        when(mockCalendar.getActiveEvent()).thenReturn(activeEvent);
        when(mockCalendar.getNextEvent()).thenReturn(nextEvent);

        presenter.onUpcomingEventClicked();

        verify(mockView).openCircuitScreen(activeEventNumber);
        verify(mockEventsLogger).logViewEvent(Events.DETAILS_CIRCUIT);
    }
}
