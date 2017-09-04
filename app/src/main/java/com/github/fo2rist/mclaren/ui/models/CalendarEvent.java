package com.github.fo2rist.mclaren.ui.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CalendarEvent implements Serializable {

    public final String circuitId;
    public final String countryCode;
    public final String trackName;
    public final String city;
    public final String grandPrixName;
    public final Date startDate;
    public final Date endDate;

    public CalendarEvent(String circuitId, String countryCode, String trackName, String city, String grandPrixName,
            Date startDate) {
        assert circuitId != null;
        assert countryCode != null;
        assert trackName != null;
        assert city != null;
        assert grandPrixName != null;
        assert startDate != null;

        this.circuitId = circuitId;
        this.countryCode = countryCode;
        this.trackName = trackName;
        this.city = city;
        this.grandPrixName = grandPrixName;
        this.startDate = startDate;
        this.endDate = calculateEndDate(startDate);
    }

    private Date calculateEndDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 2);
        return calendar.getTime();
    }
}
