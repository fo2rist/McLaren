package com.github.fo2rist.mclaren.ui.models;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.github.fo2rist.mclaren.models.Circuit;
import com.github.fo2rist.mclaren.models.Event;
import java.io.Serializable;
import org.joda.time.DateTime;

public class CalendarEvent implements Serializable {

    public final String circuitId;
    public final String countryCode;
    public final String trackName;
    public final String city;
    public final String grandPrixName;
    public final int laps;
    public final double length;
    public final double distance;
    public final String seasons;
    public final int gpHeld;
    public final String wikiLink;

    public final DateTime startDate;
    public final DateTime endDate;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public CalendarEvent(String circuitId, String countryCode, String trackName, String city, String grandPrixName,
            int laps, double length, double distance, String seasons, int gpHeld, String wikiLink, DateTime startDate) {
        this.circuitId = circuitId;
        this.countryCode = countryCode;
        this.trackName = trackName;
        this.city = city;
        this.grandPrixName = grandPrixName;
        this.laps = laps;
        this.length = length;
        this.distance = distance;
        this.seasons = seasons;
        this.gpHeld = gpHeld;
        this.wikiLink = wikiLink;
        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate);;
    }

    public CalendarEvent(@NonNull Circuit circuit, @NonNull Event grandPrixEvent) {
        assert circuit.track.equals(grandPrixEvent.track);

        this.circuitId = circuit.id;
        this.countryCode = circuit.country;
        this.trackName = circuit.track;
        this.city = circuit.city;
        this.grandPrixName = circuit.name;
        this.laps = circuit.laps;
        this.length = circuit.length;
        this.distance = circuit.distance;
        this.seasons = circuit.seasons;
        this.gpHeld = circuit.gpHeld;
        this.wikiLink = circuit.wikiLink;

        this.startDate = new DateTime(grandPrixEvent.date);
        this.endDate = calculateEndDate(startDate);
    }

    public boolean isActiveAt(DateTime time) {
        return time.compareTo(startDate) >= 0 && time.compareTo(endDate.plusHours(23)) <= 0;
        // +23H to cover all time zones
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    static DateTime calculateEndDate(DateTime startDate) {
        return startDate.plusDays(2);
    }
}
