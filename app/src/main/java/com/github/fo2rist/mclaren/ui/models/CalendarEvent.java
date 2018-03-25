package com.github.fo2rist.mclaren.ui.models;

import android.support.annotation.VisibleForTesting;

import com.github.fo2rist.mclaren.models.Circuit;
import com.github.fo2rist.mclaren.models.Event;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

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

    public final Date startDate;
    public final Date endDate;

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public CalendarEvent(String circuitId, String countryCode, String trackName, String city, String grandPrixName,
            int laps, double length, double distance, String seasons, int gpHeld, String wikiLink, Date startDate) {
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

    public CalendarEvent(Circuit circuit, Event grandPrixEvent) {
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

        this.startDate = grandPrixEvent.date;
        this.endDate = calculateEndDate(startDate);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    static Date calculateEndDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 2);
        return calendar.getTime();
    }
}
