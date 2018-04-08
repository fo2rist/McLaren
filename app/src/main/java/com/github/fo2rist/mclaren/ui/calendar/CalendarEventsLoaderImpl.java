package com.github.fo2rist.mclaren.ui.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.Circuit;
import com.github.fo2rist.mclaren.models.Event;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.LocalDate;

public class CalendarEventsLoaderImpl implements CalendarEventsLoader {

    TypeToken<ArrayList<Event>> grandPrixListType = new TypeToken<ArrayList<Event>>(){};
    TypeToken<ArrayList<Circuit>> circuitListType = new TypeToken<ArrayList<Circuit>>(){};

    Context context;

    @Inject
    public CalendarEventsLoaderImpl(Context context) {
        this.context = context;
    }

    @Override
    public RaceCalendar loadCurrentCalendar() {
        LocalDate date = new LocalDate();
        RaceCalendar currentCalendar = loadCalendar(date.getYear());
        return !currentCalendar.isEmpty()
                ? currentCalendar
                : loadCalendar(date.getYear() - 1);
    }

    /**
     * Get Grand Prix calendar for given year
     * @return calendar non empty, if year found.
     */
    @NonNull
    public RaceCalendar loadCalendar(int year) {
        List<Event> events = readJsonFromAssets(context, "calendar_" + year + ".json", grandPrixListType);
        List<Circuit> circuits = readJsonFromAssets(context, "circuits.json", circuitListType);

        RaceCalendar calendarEvents = new RaceCalendar();
        if (events == null || circuits == null) {
            return calendarEvents;
        }

        for(Event gp: events) {
            Circuit circuit = findCircuit(circuits, gp);
            calendarEvents.add(new CalendarEvent(circuit, gp));
        }
        return calendarEvents;
    }

    private Circuit findCircuit(List<Circuit> circuits, Event gp) {
        for(Circuit circuit: circuits) {
            if (circuit.track.equals(gp.track)) {
                return circuit;
            }
        }
        return null;
    }

    @Nullable
    private static <T> T readJsonFromAssets(Context context, String fileName, TypeToken<T> objectTypeToken) {
        try {
            InputStream stream = context.getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(stream);
            T result = new Gson().fromJson(reader, objectTypeToken.getType());
            stream.close();
            return result;
        } catch (IOException e) {
            //that should never happen in prod, or at least it mean apk is broken
            return null;
        }
    }
}
